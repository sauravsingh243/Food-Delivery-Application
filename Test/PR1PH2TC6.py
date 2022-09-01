from http import HTTPStatus
from threading import Thread
import requests

#2 concurrent agent sign out after 3 agents have signed in
#New posted order should be assigned to last standing agent

# RESTAURANT SERVICE    : http://localhost:8080
# DELIVERY SERVICE      : http://localhost:8081
# WALLET SERVICE        : http://localhost:8082


def t1(result):  # First concurrent request

    # Agent 201 sign in
    http_response = requests.post(
        "http://localhost:8081/agentSignOut", json={"agentId": 201})

    result["1"] = http_response


def t2(result):  # Second concurrent request

    # Agent 202 sign in
    http_response = requests.post(
        "http://localhost:8081/agentSignOut", json={"agentId": 202})

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

    if (http_response.status_code != HTTPStatus.CREATED):
        return 'Fail2'

    # Agent 203 sign in
    http_response = requests.post(
        "http://localhost:8081/agentSignIn", json={"agentId": 203})

    if (http_response.status_code != HTTPStatus.CREATED):
        return 'Fail2'

    ### Parallel Execution Begins ###
    thread1 = Thread(target=t1, kwargs={"result": result})
    thread2 = Thread(target=t2, kwargs={"result": result})

    thread1.start()
    thread2.start()

    thread1.join()
    thread2.join()

    ### Parallel Execution Ends ###

    status_code1 = result["1"].status_code

    status_code2 = result["2"].status_code

    if(status_code1 != 201):
        return 'fail1'
    if(status_code2 != 201):
        return 'fail2'

#placing an order which sould go to last standing agent 203
    http_response = requests.post(
        "http://localhost:8081/requestOrder", json={"custId": 301, "restId": 102, "itemId": 1, "qty": 1})

    if(http_response.status_code != HTTPStatus.CREATED):
        return 'fail3'

    http_response = requests.get(
        f"http://localhost:8081/agent/203")

#checking if the order placed is assigned to agent 203
    if http_response.status_code != 200:
        return 'Fail4'
    if http_response.json().get("status") != "unavailable":
        return "fail5"

    return 'Pass'


if __name__ == "__main__":

    print(test())
