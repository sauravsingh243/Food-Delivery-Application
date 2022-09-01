from http import HTTPStatus
from threading import Thread
import requests

# CHECK IF ALL AGENTS ARE AVAILABLE AND TWO ORDERS ARE PLACED
# THEN ONE OF THE AGENT SHOULD BE UNASSIGNED

# RESTAURANT SERVICE    : http://localhost:8080
# DELIVERY SERVICE      : http://localhost:8081
# WALLET SERVICE        : http://localhost:8082


def t1(result):  # First concurrent request

    # Customer 301 requests an order of item 1, quantity 3 from restaurant 101
    http_response = requests.post(
        "http://localhost:8081/requestOrder", json={"custId": 301, "restId": 101, "itemId": 1, "qty": 3})

    result["1"] = http_response


def t2(result):  # Second concurrent request

    # Customer 302 requests an order of item 1, quantity 3 from restaurant 101
    http_response = requests.post(
        "http://localhost:8081/requestOrder", json={"custId": 302, "restId": 101, "itemId": 1, "qty": 3})

    result["2"] = http_response



def test():

    result = {}

    # Reinitialize Restaurant service
    http_response = requests.post("http://localhost:8080/reInitialize")

    # Reinitialize Delivery service
    http_response = requests.post("http://localhost:8081/reInitialize")

    # Reinitialize Wallet service
    http_response = requests.post("http://localhost:8082/reInitialize")

    # Agent 201 sign in
    http_response = requests.post(
        "http://localhost:8081/agentSignIn", json={"agentId": 201})

    if(http_response.status_code != HTTPStatus.CREATED):
        return 'Fail1'

    # Agent 202 sign in
    http_response = requests.post(
        "http://localhost:8081/agentSignIn", json={"agentId": 202})

    if(http_response.status_code != HTTPStatus.CREATED):
        return 'Fail2'

    # Agent 203 sign in
    http_response = requests.post(
        "http://localhost:8081/agentSignIn", json={"agentId": 203})

    if(http_response.status_code != HTTPStatus.CREATED):
        return 'Fail3'

    ### Parallel Execution Begins ###
    thread1 = Thread(target=t1, kwargs={"result": result})
    thread2 = Thread(target=t2, kwargs={"result": result})

    thread1.start()
    thread2.start()

    thread1.join()
    thread2.join()

    ### Parallel Execution Ends ###

    order_id1 = result["1"].json().get("orderId")
    status_code1 = result["1"].status_code

    order_id2 = result["2"].json().get("orderId")
    status_code2 = result["2"].status_code

    if (status_code1 != status_code2 and status_code2 != HTTPStatus.CREATED) or order_id1 == order_id2:
        return "Fail4"

    # Check status of first order
    http_response = requests.get(
        f"http://localhost:8081/order/{order_id1}")

    if(http_response.status_code != HTTPStatus.OK):
        return 'Fail5'

    res_body = http_response.json()

    agent_id1 = res_body.get("agentId")
    order_status1 = res_body.get("status")

    # Check status of second order
    http_response = requests.get(
        f"http://localhost:8081/order/{order_id2}")

    res_body = http_response.json()

    agent_id2 = res_body.get("agentId")
    order_status2 = res_body.get("status")

    if((order_status1 != 'assigned' and order_status2 != 'assigned')):
        return 'Fail6'

    if((agent_id1 == 201 and agent_id2 == 201) or (agent_id1 == -1 and agent_id2 == -1)):
        return 'Fail7'

    # Check Agent 201 status
    http_response = requests.get("http://localhost:8081/agent/201")

    if(http_response.status_code != HTTPStatus.OK):
        return 'Fail8'

    res_body = http_response.json()

    agent_id1 = res_body.get("agentId")
    status1 = res_body.get("status")

    # Check Agent 202 status
    http_response = requests.get("http://localhost:8081/agent/202")

    if(http_response.status_code != HTTPStatus.OK):
        return 'Fail9'

    res_body = http_response.json()

    agent_id2 = res_body.get("agentId")
    status2 = res_body.get("status")

    # Check Agent 203 status
    http_response = requests.get("http://localhost:8081/agent/203")

    if(http_response.status_code != HTTPStatus.OK):
        return 'Fail10'

    res_body = http_response.json()

    agent_id3 = res_body.get("agentId")
    status3 = res_body.get("status")

    if((agent_id1 != 201 and agent_id2 != 202 and agent_id3 != 203)):
        return 'Fail10'

    if(status1 == 'available'):
        if(status2 != 'unavilable' or status3 != 'unavailable'):
            return 'fail11'
    elif(status2 == 'available'):
        if (status1 != 'unavilable' or status3 != 'unavailable'):
            return 'fail12'
    else:
        if (status1 != 'unavailable' or status2 != 'unavailable'):
            return 'fail13'

    return 'Pass'


if __name__ == "__main__":

    print(test())
