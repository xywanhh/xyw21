# -*- coding: utf-8 -*-

import functools

def log(para):
    if isinstance(para, str):
        def decorator(fn):
            @functools.wraps(fn)
            def wrapper(*args, **kw):
                print('%s call ' % para, wrapper.__name__)
                return fn(*args, **kw)
            return wrapper
        return decorator
    else:
        @functools.wraps(para)
        def wrapper(*args, **kw):
            print('call ', wrapper.__name__)
            return para(*args, **kw)
        return wrapper

@log('just')
def abc():
    print('abc')
    return 0

@log
def cba():
    print('cba')
    return 0

abc()
cba()