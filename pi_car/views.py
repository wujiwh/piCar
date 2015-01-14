'''
#=============================================================================
#     FileName: views.py
#         Desc: 
#       Author: wangheng
#        Email: wujiwh@gmail.com
#     HomePage: http://wangheng.org
#      Version: 0.0.1
#   LastChange: 2015-01-14 13:46:29
#      History:
#=============================================================================
'''
from flask import Flask, request, session, g, redirect, url_for, abort, render_template, flash
from contextlib import closing
from pi_car import app
import re
import RPi.GPIO as GPIO
@app.route('/')
def show_index():
	return render_template('home.html')

@app.route("/login", methods=["GET", "POST"])                                   
def login():                                                                    
	if request.method=="GET":                                                   
		return "get"+request.form["user"]
	elif request.method=="POST":                                                
		return "post"

@app.route('/ctl',methods=['GET','POST'])
def ctrl_id():
	if request.method == 'POST':
		id=request.form['id']
		GPIO.setmode(GPIO.BOARD)
		GPIO.setwarnings(False)
		GPIO.setup(11,GPIO.OUT)
		GPIO.setup(12,GPIO.OUT)
		GPIO.setup(15,GPIO.OUT)
		GPIO.setup(16,GPIO.OUT)

		if id == 't_left':
			t_left()
			return "left"
		elif id == 't_right':
			t_right()
			return "right"
		elif id == 't_up':
			t_up()
			return "up"
		elif id == 't_down':
			t_down()
			return "down"
		elif id == 't_stop':
			t_stop()
			return "stop"

	return redirect(url_for('show_index'))

def t_stop():
	GPIO.output(11, False)
	GPIO.output(12, False)
	GPIO.output(15, False)
	GPIO.output(16, False)

def t_up():
	GPIO.output(11, True)
	GPIO.output(12, False)
	GPIO.output(15, True)
	GPIO.output(16, False)

def t_down():
	GPIO.output(11, False)
	GPIO.output(12, True)
	GPIO.output(15, False)
	GPIO.output(16, True)

def t_left():
	GPIO.output(11, False)
	GPIO.output(12, True)
	GPIO.output(15, True)
	GPIO.output(16, False)

def t_right():
	GPIO.output(11, True)
	GPIO.output(12, False)
	GPIO.output(15, False)
	GPIO.output(16, True)

