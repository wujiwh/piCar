#!/usr/bin/python
'''
#=============================================================================
#     FileName: server.py
#         Desc: 
#       Author: wangheng
#        Email: wujiwh@gmail.com
#     HomePage: http://wangheng.org
#      Version: 0.0.1
#   LastChange: 2015-01-13 20:58:54
#      History:
#=============================================================================
'''
from pi_car import app

app.run(host='0.0.0.0',port=2000)
