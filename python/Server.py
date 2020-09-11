from flask import Flask, request
from flask_restful import Api, Resource
import Scrapper
import sys
import os
from pymongo import MongoClient

app = Flask(__name__)
api = Api(app)

@app.route("/api/stock", methods=['POST'])
def post():
   json_data = request.get_json(force=True)
   stock = {
      "sym": json_data["symbol"],
      "start": json_data["start"],
      "end": json_data["end"],
   }
   #Get Mongodb var
   mongodb_url = 'mongodb://localhost:27017/teamlion' if os.getenv('MONGODB_URL') is None else os.getenv('MONGODB_URL')
   Scrapper.scrapeData(mongodb_url, stock["sym"], stock["start"], stock["end"])
   return "", 201
@app.route("/api/stock",methods=["GET"])
def get():
    mongoUrl = 'mongodb://localhost:27017/teamlion' if os.getenv('MONGODB_URL') is None else os.getenv(
      'MONGODB_URL')
    client = MongoClient(mongoUrl)
    db = client.teamlion
    stocks = db.stock.find()
    for a in stocks:
       print(a)
    return "",200

if __name__ == '__main__':
   app.run(host='0.0.0.0')