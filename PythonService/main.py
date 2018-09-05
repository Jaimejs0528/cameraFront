#!/usr/bin/env python

"""----------1. Inicializacion del sistema e insercion de librerias---------------------------------"""

from flask import Flask, request, redirect, url_for
from werkzeug.utils import secure_filename
import json
import os
"""from app import main"""

app = Flask(__name__)
""" Ruta en donde se almacenan las imagenes en el backend"""
UPLOAD_FOLDER = '/home/lis/Desktop/Photo' 
"""Formatos de imagen permitidos"""
ALLOWED_EXTENSIONS = set(['jpg','jpeg','gif'])
""""Se aplica la configuracion a la aplicacion"""
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER 

"""Metodo para verificar el formato de la imagen"""
def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

"""----------2. Indexacion de la ruta de inicio---------------------------------"""

@app.route('/')
def index():
	return "Bienvenido, identificador de huellas digitales."
	
"""----------3. Funcionamiento ante un metodo post por la ruta /upload ---------------------------------"""
@app.route('/uploadImage', methods=['POST'])
def upload():
    """Verificacion del metodo post"""
    if request.method == 'POST': 
        """Se obtiene la imagen enviada como archivo"""
        foto=request.files['image']
        if foto and allowed_file(foto.filename):
            """Se verifica el formato por seguridad"""
            filename = secure_filename(foto.filename)
            """Se guarda la imagen en la ruta indicada"""
            foto.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
            responseData = {'code':'100','message':'Image was Upload','image':filename}
            responseJson = json.dumps(responseData)
        else:
            responseData = {'code':'80','message':'invalid type file','image':filename}
            responseJson = json.dumps(responseData)
        print responseJson
        return responseJson

"""----------4. Iniciacion del servicio---------------------------------"""
if __name__ == "__main__":
	try:
		app.run(host='192.168.193.201',debug=False,port=9999)
	except:
		raise
 
