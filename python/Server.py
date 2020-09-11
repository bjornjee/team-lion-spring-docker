from flask import Flask, request
from flask_restful import Api, Resource
import Scrapper
import sys

app = Flask(__name__)
api = Api(app)

class Stock(Resource):
   def post(self):
      json_data = request.get_json(force=True)
      stock = {
         "sym": json_data["sym"], 
         "start": json_data["start"],
         "end": json_data["end"],
      }  
      Scrapper.scrapeData('mongodb://localhost:27017/teamlion', stock["sym"], stock["start"], stock["end"])
      return stock, 201

api.add_resource(Stock, "/api/stock", "/api/stock/<int:id>")

if __name__ == '__main__':
   app.run(host='0.0.0.0')