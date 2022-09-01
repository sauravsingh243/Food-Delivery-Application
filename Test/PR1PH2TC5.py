from http import HTTPStatus
from threading import Thread
import requests

# Two concurrent orders and thus wallet deduction by same customer from same restaurant

# RESTAURANT SERVICE    : http://localhost:8080
# DELIVERY SERVICE      : http://localhost:8081
# WALLET SERVICE        : http://localhost:8082


def t1(result):  # First concurrent request

    # Customer 301 requests an order of item 1, quantity 3 from restaurant 101
    http_response = requests.post(
        "http://localhost:8081/requestOrder", json={"custId": 301, "restId": 101, "itemId": 1, "qty": 5})

    result["1"] = http_response


def t2(result):  # Second concurrent request

    # Customer 302 requests an order of item 1, quantity 10 from restaurant 101
    http_response = requests.post(
        "http://localhost:8081/requestOrder", json={"custId": 301, "restId": 101, "itemId": 1, "qty": 5})

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

    if(status_code1 != 201):
        return 'fail1'
    if(status_code2 != 201):
        return 'fail2'

    # Both the concurrent orders should be placed
    http_response = requests.get(
        f"http://localhost:8081/order/{1000}")

    if(http_response.status_code != 200):
        return 'Fail3'

    http_response = requests.get(
        f"http://localhost:8081/order/{1001}")

    if http_response.status_code != 200:
        return 'Fail4'

    # Check the wallet balance: after order of 1800 balance should be 200
    http_response = requests.get(
        f"http://localhost:8082/balance/{301}")
    if http_response.status_code != 200:
        return 'fail5'
    if http_response.json().get("balance") != 200:
        return 'fail6'



    return 'Pass'


if __name__ == "__main__":

    print(test())
