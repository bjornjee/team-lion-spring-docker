from flask import Flask, request
from flask_restful import Api, Resource
import Scrapper
import sys
import os

app = Flask(__name__)
api = Api(app)

class Stock(Resource):
   def post(self):
      json_data = request.get_json(force=True)
      stock = {
         "sym": json_data["symbol"],
         "start": json_data["start"],
         "end": json_data["end"],
      }
      #Get Mongodb var
      mongodb_url = 'mongodb://localhost:27017/teamlion' if os.getenv('MONGODB_URL') is None else os.getenv('MONGODB_URL')
      Scrapper.scrapeData(mongodb_url, stock["sym"], stock["start"], stock["end"])
      return stock, 201

api.add_resource(Stock, "/api/stock", "/api/stock/<int:id>")

if __name__ == '__main__':
   app.run(host='0.0.0.0')