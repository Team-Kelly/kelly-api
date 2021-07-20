#-*- encoding: utf-8 -*-

import sys
import time, sys, base64;start = time.time()
import json
from pprint import pprint
import requests
from bs4 import BeautifulSoup

input = args[0]

output = "{ "


html = requests.get("https://finance.naver.com/item/main.nhn?code="+input)

soup = BeautifulSoup(html.text, 'html.parser')

data1 = soup.find('div', {'class': 'new_totalinfo'})

data2 = data1.find_all('dd')

#현재가

stockinfo_beforeconv = data2[3].text
stockinfo = stockinfo_beforeconv.split()

find_curprice = stockinfo[1]
output += "\"curPrice\" : \"" +find_curprice +"\", "


#전일가

stockinfo_beforeconv = data2[4].text
stockinfo = stockinfo_beforeconv.split()

find_prevprice = stockinfo[1]
output += "\"prevPrice\" : \"" +find_prevprice +"\", "


#시가

stockinfo_beforeconv = data2[5].text
stockinfo = stockinfo_beforeconv.split()

find_startprice = stockinfo[1]
output += "\"startPrice\" : \"" +find_startprice +"\", "


#고가

stockinfo_beforeconv = data2[6].text
stockinfo = stockinfo_beforeconv.split()

find_maxprice = stockinfo[1]
output += "\"maxPrice\" : \"" +find_maxprice +"\", "


#저가

stockinfo_beforeconv = data2[8].text
stockinfo = stockinfo_beforeconv.split()

find_minprice = stockinfo[1]
output += "\"minPrice\" : \"" +find_minprice +"\", "


#거래량

stockinfo_beforeconv = data2[10].text
stockinfo = stockinfo_beforeconv.split()

find_volume = stockinfo[1]
output += "\"volume\" : \"" +find_volume +"\" }"



print(base64.b64encode(output.encode('utf-8')))