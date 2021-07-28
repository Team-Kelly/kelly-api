# .py
# import json
# import sys
#
# result = sys.argv[1]
# print(result)

#-*- encoding: utf-8 -*-
from bs4 import BeautifulSoup
import time, sys, base64;start = time.time()
from pprint import pprint
import requests
import sys
import json

arsid = args[0]
busid = args[1]

output = "{ "


html = requests.get("https://bus.go.kr/xmlRequest/getStationByUid.jsp?strBusNumber="+arsid)

soup = BeautifulSoup(html.text, 'lxml')


for stationlist in soup.find_all('stationlist'):

    if (stationlist.find_all('rtnm')[0].text == busid):

        arrmsg1 = stationlist.find_all('arrmsg1')[0].text
        output += "\"arrmsg1\" : \"" +arrmsg1 +"\", "

        arrmsg2 = stationlist.find_all('arrmsg2')[0].text
        output += "\"arrmsg2\" : \"" +arrmsg2 +"\" }"

        break


print(base64.b64encode(output.encode('utf-8')))