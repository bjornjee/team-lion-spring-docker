# -*- coding: utf-8 -*-
"""
Created on Fri Sep  4 07:18:46 2020

@author: Administrator
"""

#imports
import yfinance as yf
from pymongo import MongoClient
import sys
import json
import pandas as pd
from datetime import datetime


class Scrapper:
    
    def __init__(self,mongoUrl):
        client = MongoClient(mongoUrl)
        db = client.teamlion
        self.stock = db.stock
    
    def downloadStockData(self,symbol,start,end):
        #Check if data already exists in database
        df = yf.download(symbol,start=start,end=end)
        df['symbol'] = symbol
        df.index = pd.to_datetime(df.index)
        df['date']=list(map(lambda x:'{}-{:0>2}-{:0>2}'.format(x.year,x.month,x.day),df.index))
        col = list(map(lambda x:x.lower(),df.columns))
        df.columns = col
        #Get count in mongo to increment id
        last_id = self.stock.find({}).count()
        ids = list(range(last_id+1,last_id+len(df.index)+1))
        #print(ids)
        #print(df.head())
        df['_id'] = ids
        #print(df.head())
        #store into database
        records = json.loads(df.T.to_json()).values()
        #print(records)
        #print json for java service
        print(df.to_json(orient="records"))
        if (not records):
            self.stock.insert_many(records)

def scrapeData(url, sym, start, end):
    print('''
           {},{},{},{}
           '''.format(url,sym,start,end))
    service = Scrapper(url)
    service.downloadStockData(sym,start,end)
    end_time = datetime.now()     
        
if __name__ =='__main__':
    start_time = datetime.now()
    url = sys.argv[1]
    sym = sys.argv[2]
    start = sys.argv[3]
    end = sys.argv[4]
    print('''
           {},{},{},{}
           '''.format(url,sym,start,end))
    service = Scrapper(url)
    service.downloadStockData(sym,start,end)
    end_time = datetime.now()
    '''
    with open('./time.txt','a') as file:
        file.write('{}'.format(end_time-start_time))
    '''
