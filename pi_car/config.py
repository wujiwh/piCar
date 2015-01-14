'''
#=============================================================================
#     FileName: config.py
#         Desc: 
#       Author: wangheng
#        Email: wujiwh@gmail.com
#     HomePage: http://wangheng.org
#      Version: 0.0.1
#   LastChange: 2015-01-14 13:49:32
#      History:
#=============================================================================
'''
import os
class Config(object):
  BASEDIR = os.path.abspath(os.path.dirname(__file__))
  DATABASE = BASEDIR+'/var/pi.db'
  SECRET_KEY = os.urandom(24)
  USERNAME = 'admin'
  PASSWORD = 'default'
  DEBUG = True
  HOST = '0.0.0.0'
  PORT = 2000
class Development(Config):
  pass
class Production(Config):
  DEBUG = False
  HOST = '0.0.0.0'
  PORT = 8080
