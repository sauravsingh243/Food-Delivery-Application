from http import HTTPStatus
from threading import Thread
import requests

# Check if only one order is assigned when multiple concurrent requests
# for order comes, when only one delivery agent is available
# but one of the order is not placed due to insufficient inventory of itemId = 1

# RESTAURANT SERVICE    : http://localhost:8080
# DELIVERY SERVICE      : http://localhost:8081
# WALLET SERVICE        : http://localhost:8082


def t1(result):  # First concurrent request

    # Customer 301 requests an order of item 1, quantity 3 from restaurant 101
    http_response = requests.post(
        "http://localhost:8081/requestOrder", json={"custId": 301, "restId": 101, "itemId": 1, "qty": 3})

    result["1"] = http_response


def t2(result):  # Second concurrent request

    # Customer 302 requests an order of item 1, quantity 10 from restaurant 101
    http_response = requests.post(
        "http://localhost:8081/requestOrder", json={"custId": 302, "restId": 101, "itemId": 1, "qty": 10})

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

    ### Parallel Execution Begins ###
    thread1 = Thread(target=t1, kwargs={"result": result})
    thread2 = Thread(target=t2, kwargs={"result": result})

    thread1.start()
    thread2.start()

    thread1.join()
    thread2.join()

    ### Parallel Execution Ends ###

    status_code1 = result["1"].status_code

    # order_id2 = result["2"].json().get("orderId")
    status_code2 = result["2"].status_code

    if(status_code1 == 201):
        order_id1 = result["1"].json().get("orderId")
        if(order_id1 != 1000 or status_code2 != 410):
            return 'fail1'
    else:
        order_id1 = result["2"].json().get("orderId")
        if (order_id1 != 1000 or status_code1 != 410):
            return 'fail2'

    # Check status of unplaced order
    http_response = requests.get(
        f"http://localhost:8081/order/{1001}")

    if(http_response.status_code != HTTPStatus.NOT_FOUND):
        return 'Fail3'

    # Check status of first order
    http_response = requests.get(
        f"http://localhost:8081/order/{order_id1}")

    if (http_response.status_code != HTTPStatus.OK):
        return 'Fail4'
    res_body = http_response.json()

    agent_id1 = res_body.get("agentId")
    order_status1 = res_body.get("status")

    if(order_status1 == 'unassigned'):
        return 'Fail5'

    if(agent_id1 == -1):
        return 'Fail6'

    return 'Pass'


if __name__ == "__main__":

    print(test())
