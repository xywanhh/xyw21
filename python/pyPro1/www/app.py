#!/usr/bin/env python
# -*- coding:utf-8 -*-
'web main'
__author__ = 'xywhh'

'''
web applicaton
'''

import logging
logging.basicConfig(level=logging.INFO)

import asyncio, os, json, time
from datetime import datetime
from aiohttp import web

def index(request):
    return web.Response(body=b'<h1>hello</h1>', content_type='text/html')


# @asyncio.coroutine 在版本3已经改成async def的格式，更加简便
# @asyncio.coroutine
async def init(loop):
    # app = web.Application(loop=loop)
    app = web.Application()
    app.router.add_route('GET', '/', index)
    # srv = yield from loop.create_server(app.make_handler(), '127.0.0.1', 9000) # python2
    # srv = await loop.create_server(app.make_handler(), '127.0.0.1', 9000)
    apprunner = web.AppRunner(app)
    await apprunner.setup()
    srv = await loop.create_server(apprunner.server, '127.0.0.1', 9000)
    logging.info('server started at http://127.0.0.1:9000')
    return srv

loop = asyncio.get_event_loop()
loop.run_until_complete(init(loop))
loop.run_forever()







