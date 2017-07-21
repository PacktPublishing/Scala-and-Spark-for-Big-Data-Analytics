from yahoo_finance import Share
from pprint import pprint

msoft = Share('MSFT')
print(msoft.get_open())
print(msoft.get_price())

print(msoft.get_trade_datetime())

msoft.refresh()
print(msoft.get_price())
print(msoft.get_trade_datetime())

pprint(msoft.get_historical('2016-07-10', '2016-07-14'))
