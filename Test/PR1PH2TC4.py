from http import HTTPStatus
from threading import Thread
import requests

# Two concurrent item refill for the same restaurant(id=102) and same item(id=1) then placing and big order which will empty the quantity

# RESTAURANT SERVICE    : http://localhost:8080
# DELIVERY SERVICE      : http://localhost:8081
# WALLET SERVICE        : http://localhost:8082


def t1(result):  # First concurrent request

    # Refill item in rest 102 qty=10
    http_response = requests.post(
        "http://localhost:8080/refillItem", json={"restId": 102, "itemId": 1, "qty": 10})

    result["1"] = http_response #code 201


def t2(result):  # Second concurrent request

    # Refill item in rest 102 qty=5
    http_response = requests.post(
        "http://localhost:8080/refillItem", json={"restId": 102, "itemId": 1, "qty": 5})

    result["2"] = http_response #code 201


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

    status_code2 = result["2"].status_code

    if(status_code1 != 201):
        return 'fail1'
    if(status_code2 != 201):
        return 'fail2'

    #Placing 1 big order of 25 quantity
    http_response3 = requests.post(
        "http://localhost:8081/requestOrder", json={"custId": 301, "restId": 102, "itemId": 1, "qty": 25})
    if (http_response.status_code != HTTPStatus.CREATED):
        return 'Fail3'
    orderId = http_response3.json().get("orderId")
    if(orderId != 1000):
        return 'Fail4'

    return 'Pass'


if __name__ == "__main__":

    print(test())
