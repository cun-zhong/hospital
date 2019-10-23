/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 0);
/******/ })
/************************************************************************/
/******/ ({

/***/ "./config/production.entry.js":
/*!************************************!*\
  !*** ./config/production.entry.js ***!
  \************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var Es6Promise = __webpack_require__(/*! es6-promise */ "./node_modules/_es6-promise@4.2.5@es6-promise/dist/es6-promise.js");
Es6Promise.polyfill();

/***/ }),

/***/ "./node_modules/_es6-promise@4.2.5@es6-promise/dist/es6-promise.js":
/*!*************************************************************************!*\
  !*** ./node_modules/_es6-promise@4.2.5@es6-promise/dist/es6-promise.js ***!
  \*************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

/*!
 * @overview es6-promise - a tiny implementation of Promises/A+.
 * @copyright Copyright (c) 2014 Yehuda Katz, Tom Dale, Stefan Penner and contributors (Conversion to ES6 API by Jake Archibald)
 * @license   Licensed under MIT license
 *            See https://raw.githubusercontent.com/stefanpenner/es6-promise/master/LICENSE
 * @version   v4.2.5+7f2b526d
 */

(function (global, factory) {
	typeof exports === 'object' && typeof module !== 'undefined' ? module.exports = factory() :
	typeof define === 'function' && define.amd ? define(factory) :
	(global.ES6Promise = factory());
}(this, (function () { 'use strict';

function objectOrFunction(x) {
  var type = typeof x;
  return x !== null && (type === 'object' || type === 'function');
}

function isFunction(x) {
  return typeof x === 'function';
}



var _isArray = void 0;
if (Array.isArray) {
  _isArray = Array.isArray;
} else {
  _isArray = function (x) {
    return Object.prototype.toString.call(x) === '[object Array]';
  };
}

var isArray = _isArray;

var len = 0;
var vertxNext = void 0;
var customSchedulerFn = void 0;

var asap = function asap(callback, arg) {
  queue[len] = callback;
  queue[len + 1] = arg;
  len += 2;
  if (len === 2) {
    // If len is 2, that means that we need to schedule an async flush.
    // If additional callbacks are queued before the queue is flushed, they
    // will be processed by this flush that we are scheduling.
    if (customSchedulerFn) {
      customSchedulerFn(flush);
    } else {
      scheduleFlush();
    }
  }
};

function setScheduler(scheduleFn) {
  customSchedulerFn = scheduleFn;
}

function setAsap(asapFn) {
  asap = asapFn;
}

var browserWindow = typeof window !== 'undefined' ? window : undefined;
var browserGlobal = browserWindow || {};
var BrowserMutationObserver = browserGlobal.MutationObserver || browserGlobal.WebKitMutationObserver;
var isNode = typeof self === 'undefined' && typeof process !== 'undefined' && {}.toString.call(process) === '[object process]';

// test for web worker but not in IE10
var isWorker = typeof Uint8ClampedArray !== 'undefined' && typeof importScripts !== 'undefined' && typeof MessageChannel !== 'undefined';

// node
function useNextTick() {
  // node version 0.10.x displays a deprecation warning when nextTick is used recursively
  // see https://github.com/cujojs/when/issues/410 for details
  return function () {
    return process.nextTick(flush);
  };
}

// vertx
function useVertxTimer() {
  if (typeof vertxNext !== 'undefined') {
    return function () {
      vertxNext(flush);
    };
  }

  return useSetTimeout();
}

function useMutationObserver() {
  var iterations = 0;
  var observer = new BrowserMutationObserver(flush);
  var node = document.createTextNode('');
  observer.observe(node, { characterData: true });

  return function () {
    node.data = iterations = ++iterations % 2;
  };
}

// web worker
function useMessageChannel() {
  var channel = new MessageChannel();
  channel.port1.onmessage = flush;
  return function () {
    return channel.port2.postMessage(0);
  };
}

function useSetTimeout() {
  // Store setTimeout reference so es6-promise will be unaffected by
  // other code modifying setTimeout (like sinon.useFakeTimers())
  var globalSetTimeout = setTimeout;
  return function () {
    return globalSetTimeout(flush, 1);
  };
}

var queue = new Array(1000);
function flush() {
  for (var i = 0; i < len; i += 2) {
    var callback = queue[i];
    var arg = queue[i + 1];

    callback(arg);

    queue[i] = undefined;
    queue[i + 1] = undefined;
  }

  len = 0;
}

function attemptVertx() {
  try {
    var vertx = Function('return this')().require('vertx');
    vertxNext = vertx.runOnLoop || vertx.runOnContext;
    return useVertxTimer();
  } catch (e) {
    return useSetTimeout();
  }
}

var scheduleFlush = void 0;
// Decide what async method to use to triggering processing of queued callbacks:
if (isNode) {
  scheduleFlush = useNextTick();
} else if (BrowserMutationObserver) {
  scheduleFlush = useMutationObserver();
} else if (isWorker) {
  scheduleFlush = useMessageChannel();
} else if (browserWindow === undefined && typeof require === 'function') {
  scheduleFlush = attemptVertx();
} else {
  scheduleFlush = useSetTimeout();
}

function then(onFulfillment, onRejection) {
  var parent = this;

  var child = new this.constructor(noop);

  if (child[PROMISE_ID] === undefined) {
    makePromise(child);
  }

  var _state = parent._state;


  if (_state) {
    var callback = arguments[_state - 1];
    asap(function () {
      return invokeCallback(_state, child, callback, parent._result);
    });
  } else {
    subscribe(parent, child, onFulfillment, onRejection);
  }

  return child;
}

/**
  `Promise.resolve` returns a promise that will become resolved with the
  passed `value`. It is shorthand for the following:

  ```javascript
  let promise = new Promise(function(resolve, reject){
    resolve(1);
  });

  promise.then(function(value){
    // value === 1
  });
  ```

  Instead of writing the above, your code now simply becomes the following:

  ```javascript
  let promise = Promise.resolve(1);

  promise.then(function(value){
    // value === 1
  });
  ```

  @method resolve
  @static
  @param {Any} value value that the returned promise will be resolved with
  Useful for tooling.
  @return {Promise} a promise that will become fulfilled with the given
  `value`
*/
function resolve$1(object) {
  /*jshint validthis:true */
  var Constructor = this;

  if (object && typeof object === 'object' && object.constructor === Constructor) {
    return object;
  }

  var promise = new Constructor(noop);
  resolve(promise, object);
  return promise;
}

var PROMISE_ID = Math.random().toString(36).substring(2);

function noop() {}

var PENDING = void 0;
var FULFILLED = 1;
var REJECTED = 2;

var TRY_CATCH_ERROR = { error: null };

function selfFulfillment() {
  return new TypeError("You cannot resolve a promise with itself");
}

function cannotReturnOwn() {
  return new TypeError('A promises callback cannot return that same promise.');
}

function getThen(promise) {
  try {
    return promise.then;
  } catch (error) {
    TRY_CATCH_ERROR.error = error;
    return TRY_CATCH_ERROR;
  }
}

function tryThen(then$$1, value, fulfillmentHandler, rejectionHandler) {
  try {
    then$$1.call(value, fulfillmentHandler, rejectionHandler);
  } catch (e) {
    return e;
  }
}

function handleForeignThenable(promise, thenable, then$$1) {
  asap(function (promise) {
    var sealed = false;
    var error = tryThen(then$$1, thenable, function (value) {
      if (sealed) {
        return;
      }
      sealed = true;
      if (thenable !== value) {
        resolve(promise, value);
      } else {
        fulfill(promise, value);
      }
    }, function (reason) {
      if (sealed) {
        return;
      }
      sealed = true;

      reject(promise, reason);
    }, 'Settle: ' + (promise._label || ' unknown promise'));

    if (!sealed && error) {
      sealed = true;
      reject(promise, error);
    }
  }, promise);
}

function handleOwnThenable(promise, thenable) {
  if (thenable._state === FULFILLED) {
    fulfill(promise, thenable._result);
  } else if (thenable._state === REJECTED) {
    reject(promise, thenable._result);
  } else {
    subscribe(thenable, undefined, function (value) {
      return resolve(promise, value);
    }, function (reason) {
      return reject(promise, reason);
    });
  }
}

function handleMaybeThenable(promise, maybeThenable, then$$1) {
  if (maybeThenable.constructor === promise.constructor && then$$1 === then && maybeThenable.constructor.resolve === resolve$1) {
    handleOwnThenable(promise, maybeThenable);
  } else {
    if (then$$1 === TRY_CATCH_ERROR) {
      reject(promise, TRY_CATCH_ERROR.error);
      TRY_CATCH_ERROR.error = null;
    } else if (then$$1 === undefined) {
      fulfill(promise, maybeThenable);
    } else if (isFunction(then$$1)) {
      handleForeignThenable(promise, maybeThenable, then$$1);
    } else {
      fulfill(promise, maybeThenable);
    }
  }
}

function resolve(promise, value) {
  if (promise === value) {
    reject(promise, selfFulfillment());
  } else if (objectOrFunction(value)) {
    handleMaybeThenable(promise, value, getThen(value));
  } else {
    fulfill(promise, value);
  }
}

function publishRejection(promise) {
  if (promise._onerror) {
    promise._onerror(promise._result);
  }

  publish(promise);
}

function fulfill(promise, value) {
  if (promise._state !== PENDING) {
    return;
  }

  promise._result = value;
  promise._state = FULFILLED;

  if (promise._subscribers.length !== 0) {
    asap(publish, promise);
  }
}

function reject(promise, reason) {
  if (promise._state !== PENDING) {
    return;
  }
  promise._state = REJECTED;
  promise._result = reason;

  asap(publishRejection, promise);
}

function subscribe(parent, child, onFulfillment, onRejection) {
  var _subscribers = parent._subscribers;
  var length = _subscribers.length;


  parent._onerror = null;

  _subscribers[length] = child;
  _subscribers[length + FULFILLED] = onFulfillment;
  _subscribers[length + REJECTED] = onRejection;

  if (length === 0 && parent._state) {
    asap(publish, parent);
  }
}

function publish(promise) {
  var subscribers = promise._subscribers;
  var settled = promise._state;

  if (subscribers.length === 0) {
    return;
  }

  var child = void 0,
      callback = void 0,
      detail = promise._result;

  for (var i = 0; i < subscribers.length; i += 3) {
    child = subscribers[i];
    callback = subscribers[i + settled];

    if (child) {
      invokeCallback(settled, child, callback, detail);
    } else {
      callback(detail);
    }
  }

  promise._subscribers.length = 0;
}

function tryCatch(callback, detail) {
  try {
    return callback(detail);
  } catch (e) {
    TRY_CATCH_ERROR.error = e;
    return TRY_CATCH_ERROR;
  }
}

function invokeCallback(settled, promise, callback, detail) {
  var hasCallback = isFunction(callback),
      value = void 0,
      error = void 0,
      succeeded = void 0,
      failed = void 0;

  if (hasCallback) {
    value = tryCatch(callback, detail);

    if (value === TRY_CATCH_ERROR) {
      failed = true;
      error = value.error;
      value.error = null;
    } else {
      succeeded = true;
    }

    if (promise === value) {
      reject(promise, cannotReturnOwn());
      return;
    }
  } else {
    value = detail;
    succeeded = true;
  }

  if (promise._state !== PENDING) {
    // noop
  } else if (hasCallback && succeeded) {
    resolve(promise, value);
  } else if (failed) {
    reject(promise, error);
  } else if (settled === FULFILLED) {
    fulfill(promise, value);
  } else if (settled === REJECTED) {
    reject(promise, value);
  }
}

function initializePromise(promise, resolver) {
  try {
    resolver(function resolvePromise(value) {
      resolve(promise, value);
    }, function rejectPromise(reason) {
      reject(promise, reason);
    });
  } catch (e) {
    reject(promise, e);
  }
}

var id = 0;
function nextId() {
  return id++;
}

function makePromise(promise) {
  promise[PROMISE_ID] = id++;
  promise._state = undefined;
  promise._result = undefined;
  promise._subscribers = [];
}

function validationError() {
  return new Error('Array Methods must be provided an Array');
}

var Enumerator = function () {
  function Enumerator(Constructor, input) {
    this._instanceConstructor = Constructor;
    this.promise = new Constructor(noop);

    if (!this.promise[PROMISE_ID]) {
      makePromise(this.promise);
    }

    if (isArray(input)) {
      this.length = input.length;
      this._remaining = input.length;

      this._result = new Array(this.length);

      if (this.length === 0) {
        fulfill(this.promise, this._result);
      } else {
        this.length = this.length || 0;
        this._enumerate(input);
        if (this._remaining === 0) {
          fulfill(this.promise, this._result);
        }
      }
    } else {
      reject(this.promise, validationError());
    }
  }

  Enumerator.prototype._enumerate = function _enumerate(input) {
    for (var i = 0; this._state === PENDING && i < input.length; i++) {
      this._eachEntry(input[i], i);
    }
  };

  Enumerator.prototype._eachEntry = function _eachEntry(entry, i) {
    var c = this._instanceConstructor;
    var resolve$$1 = c.resolve;


    if (resolve$$1 === resolve$1) {
      var _then = getThen(entry);

      if (_then === then && entry._state !== PENDING) {
        this._settledAt(entry._state, i, entry._result);
      } else if (typeof _then !== 'function') {
        this._remaining--;
        this._result[i] = entry;
      } else if (c === Promise$1) {
        var promise = new c(noop);
        handleMaybeThenable(promise, entry, _then);
        this._willSettleAt(promise, i);
      } else {
        this._willSettleAt(new c(function (resolve$$1) {
          return resolve$$1(entry);
        }), i);
      }
    } else {
      this._willSettleAt(resolve$$1(entry), i);
    }
  };

  Enumerator.prototype._settledAt = function _settledAt(state, i, value) {
    var promise = this.promise;


    if (promise._state === PENDING) {
      this._remaining--;

      if (state === REJECTED) {
        reject(promise, value);
      } else {
        this._result[i] = value;
      }
    }

    if (this._remaining === 0) {
      fulfill(promise, this._result);
    }
  };

  Enumerator.prototype._willSettleAt = function _willSettleAt(promise, i) {
    var enumerator = this;

    subscribe(promise, undefined, function (value) {
      return enumerator._settledAt(FULFILLED, i, value);
    }, function (reason) {
      return enumerator._settledAt(REJECTED, i, reason);
    });
  };

  return Enumerator;
}();

/**
  `Promise.all` accepts an array of promises, and returns a new promise which
  is fulfilled with an array of fulfillment values for the passed promises, or
  rejected with the reason of the first passed promise to be rejected. It casts all
  elements of the passed iterable to promises as it runs this algorithm.

  Example:

  ```javascript
  let promise1 = resolve(1);
  let promise2 = resolve(2);
  let promise3 = resolve(3);
  let promises = [ promise1, promise2, promise3 ];

  Promise.all(promises).then(function(array){
    // The array here would be [ 1, 2, 3 ];
  });
  ```

  If any of the `promises` given to `all` are rejected, the first promise
  that is rejected will be given as an argument to the returned promises's
  rejection handler. For example:

  Example:

  ```javascript
  let promise1 = resolve(1);
  let promise2 = reject(new Error("2"));
  let promise3 = reject(new Error("3"));
  let promises = [ promise1, promise2, promise3 ];

  Promise.all(promises).then(function(array){
    // Code here never runs because there are rejected promises!
  }, function(error) {
    // error.message === "2"
  });
  ```

  @method all
  @static
  @param {Array} entries array of promises
  @param {String} label optional string for labeling the promise.
  Useful for tooling.
  @return {Promise} promise that is fulfilled when all `promises` have been
  fulfilled, or rejected if any of them become rejected.
  @static
*/
function all(entries) {
  return new Enumerator(this, entries).promise;
}

/**
  `Promise.race` returns a new promise which is settled in the same way as the
  first passed promise to settle.

  Example:

  ```javascript
  let promise1 = new Promise(function(resolve, reject){
    setTimeout(function(){
      resolve('promise 1');
    }, 200);
  });

  let promise2 = new Promise(function(resolve, reject){
    setTimeout(function(){
      resolve('promise 2');
    }, 100);
  });

  Promise.race([promise1, promise2]).then(function(result){
    // result === 'promise 2' because it was resolved before promise1
    // was resolved.
  });
  ```

  `Promise.race` is deterministic in that only the state of the first
  settled promise matters. For example, even if other promises given to the
  `promises` array argument are resolved, but the first settled promise has
  become rejected before the other promises became fulfilled, the returned
  promise will become rejected:

  ```javascript
  let promise1 = new Promise(function(resolve, reject){
    setTimeout(function(){
      resolve('promise 1');
    }, 200);
  });

  let promise2 = new Promise(function(resolve, reject){
    setTimeout(function(){
      reject(new Error('promise 2'));
    }, 100);
  });

  Promise.race([promise1, promise2]).then(function(result){
    // Code here never runs
  }, function(reason){
    // reason.message === 'promise 2' because promise 2 became rejected before
    // promise 1 became fulfilled
  });
  ```

  An example real-world use case is implementing timeouts:

  ```javascript
  Promise.race([ajax('foo.json'), timeout(5000)])
  ```

  @method race
  @static
  @param {Array} promises array of promises to observe
  Useful for tooling.
  @return {Promise} a promise which settles in the same way as the first passed
  promise to settle.
*/
function race(entries) {
  /*jshint validthis:true */
  var Constructor = this;

  if (!isArray(entries)) {
    return new Constructor(function (_, reject) {
      return reject(new TypeError('You must pass an array to race.'));
    });
  } else {
    return new Constructor(function (resolve, reject) {
      var length = entries.length;
      for (var i = 0; i < length; i++) {
        Constructor.resolve(entries[i]).then(resolve, reject);
      }
    });
  }
}

/**
  `Promise.reject` returns a promise rejected with the passed `reason`.
  It is shorthand for the following:

  ```javascript
  let promise = new Promise(function(resolve, reject){
    reject(new Error('WHOOPS'));
  });

  promise.then(function(value){
    // Code here doesn't run because the promise is rejected!
  }, function(reason){
    // reason.message === 'WHOOPS'
  });
  ```

  Instead of writing the above, your code now simply becomes the following:

  ```javascript
  let promise = Promise.reject(new Error('WHOOPS'));

  promise.then(function(value){
    // Code here doesn't run because the promise is rejected!
  }, function(reason){
    // reason.message === 'WHOOPS'
  });
  ```

  @method reject
  @static
  @param {Any} reason value that the returned promise will be rejected with.
  Useful for tooling.
  @return {Promise} a promise rejected with the given `reason`.
*/
function reject$1(reason) {
  /*jshint validthis:true */
  var Constructor = this;
  var promise = new Constructor(noop);
  reject(promise, reason);
  return promise;
}

function needsResolver() {
  throw new TypeError('You must pass a resolver function as the first argument to the promise constructor');
}

function needsNew() {
  throw new TypeError("Failed to construct 'Promise': Please use the 'new' operator, this object constructor cannot be called as a function.");
}

/**
  Promise objects represent the eventual result of an asynchronous operation. The
  primary way of interacting with a promise is through its `then` method, which
  registers callbacks to receive either a promise's eventual value or the reason
  why the promise cannot be fulfilled.

  Terminology
  -----------

  - `promise` is an object or function with a `then` method whose behavior conforms to this specification.
  - `thenable` is an object or function that defines a `then` method.
  - `value` is any legal JavaScript value (including undefined, a thenable, or a promise).
  - `exception` is a value that is thrown using the throw statement.
  - `reason` is a value that indicates why a promise was rejected.
  - `settled` the final resting state of a promise, fulfilled or rejected.

  A promise can be in one of three states: pending, fulfilled, or rejected.

  Promises that are fulfilled have a fulfillment value and are in the fulfilled
  state.  Promises that are rejected have a rejection reason and are in the
  rejected state.  A fulfillment value is never a thenable.

  Promises can also be said to *resolve* a value.  If this value is also a
  promise, then the original promise's settled state will match the value's
  settled state.  So a promise that *resolves* a promise that rejects will
  itself reject, and a promise that *resolves* a promise that fulfills will
  itself fulfill.


  Basic Usage:
  ------------

  ```js
  let promise = new Promise(function(resolve, reject) {
    // on success
    resolve(value);

    // on failure
    reject(reason);
  });

  promise.then(function(value) {
    // on fulfillment
  }, function(reason) {
    // on rejection
  });
  ```

  Advanced Usage:
  ---------------

  Promises shine when abstracting away asynchronous interactions such as
  `XMLHttpRequest`s.

  ```js
  function getJSON(url) {
    return new Promise(function(resolve, reject){
      let xhr = new XMLHttpRequest();

      xhr.open('GET', url);
      xhr.onreadystatechange = handler;
      xhr.responseType = 'json';
      xhr.setRequestHeader('Accept', 'application/json');
      xhr.send();

      function handler() {
        if (this.readyState === this.DONE) {
          if (this.status === 200) {
            resolve(this.response);
          } else {
            reject(new Error('getJSON: `' + url + '` failed with status: [' + this.status + ']'));
          }
        }
      };
    });
  }

  getJSON('/posts.json').then(function(json) {
    // on fulfillment
  }, function(reason) {
    // on rejection
  });
  ```

  Unlike callbacks, promises are great composable primitives.

  ```js
  Promise.all([
    getJSON('/posts'),
    getJSON('/comments')
  ]).then(function(values){
    values[0] // => postsJSON
    values[1] // => commentsJSON

    return values;
  });
  ```

  @class Promise
  @param {Function} resolver
  Useful for tooling.
  @constructor
*/

var Promise$1 = function () {
  function Promise(resolver) {
    this[PROMISE_ID] = nextId();
    this._result = this._state = undefined;
    this._subscribers = [];

    if (noop !== resolver) {
      typeof resolver !== 'function' && needsResolver();
      this instanceof Promise ? initializePromise(this, resolver) : needsNew();
    }
  }

  /**
  The primary way of interacting with a promise is through its `then` method,
  which registers callbacks to receive either a promise's eventual value or the
  reason why the promise cannot be fulfilled.
   ```js
  findUser().then(function(user){
    // user is available
  }, function(reason){
    // user is unavailable, and you are given the reason why
  });
  ```
   Chaining
  --------
   The return value of `then` is itself a promise.  This second, 'downstream'
  promise is resolved with the return value of the first promise's fulfillment
  or rejection handler, or rejected if the handler throws an exception.
   ```js
  findUser().then(function (user) {
    return user.name;
  }, function (reason) {
    return 'default name';
  }).then(function (userName) {
    // If `findUser` fulfilled, `userName` will be the user's name, otherwise it
    // will be `'default name'`
  });
   findUser().then(function (user) {
    throw new Error('Found user, but still unhappy');
  }, function (reason) {
    throw new Error('`findUser` rejected and we're unhappy');
  }).then(function (value) {
    // never reached
  }, function (reason) {
    // if `findUser` fulfilled, `reason` will be 'Found user, but still unhappy'.
    // If `findUser` rejected, `reason` will be '`findUser` rejected and we're unhappy'.
  });
  ```
  If the downstream promise does not specify a rejection handler, rejection reasons will be propagated further downstream.
   ```js
  findUser().then(function (user) {
    throw new PedagogicalException('Upstream error');
  }).then(function (value) {
    // never reached
  }).then(function (value) {
    // never reached
  }, function (reason) {
    // The `PedgagocialException` is propagated all the way down to here
  });
  ```
   Assimilation
  ------------
   Sometimes the value you want to propagate to a downstream promise can only be
  retrieved asynchronously. This can be achieved by returning a promise in the
  fulfillment or rejection handler. The downstream promise will then be pending
  until the returned promise is settled. This is called *assimilation*.
   ```js
  findUser().then(function (user) {
    return findCommentsByAuthor(user);
  }).then(function (comments) {
    // The user's comments are now available
  });
  ```
   If the assimliated promise rejects, then the downstream promise will also reject.
   ```js
  findUser().then(function (user) {
    return findCommentsByAuthor(user);
  }).then(function (comments) {
    // If `findCommentsByAuthor` fulfills, we'll have the value here
  }, function (reason) {
    // If `findCommentsByAuthor` rejects, we'll have the reason here
  });
  ```
   Simple Example
  --------------
   Synchronous Example
   ```javascript
  let result;
   try {
    result = findResult();
    // success
  } catch(reason) {
    // failure
  }
  ```
   Errback Example
   ```js
  findResult(function(result, err){
    if (err) {
      // failure
    } else {
      // success
    }
  });
  ```
   Promise Example;
   ```javascript
  findResult().then(function(result){
    // success
  }, function(reason){
    // failure
  });
  ```
   Advanced Example
  --------------
   Synchronous Example
   ```javascript
  let author, books;
   try {
    author = findAuthor();
    books  = findBooksByAuthor(author);
    // success
  } catch(reason) {
    // failure
  }
  ```
   Errback Example
   ```js
   function foundBooks(books) {
   }
   function failure(reason) {
   }
   findAuthor(function(author, err){
    if (err) {
      failure(err);
      // failure
    } else {
      try {
        findBoooksByAuthor(author, function(books, err) {
          if (err) {
            failure(err);
          } else {
            try {
              foundBooks(books);
            } catch(reason) {
              failure(reason);
            }
          }
        });
      } catch(error) {
        failure(err);
      }
      // success
    }
  });
  ```
   Promise Example;
   ```javascript
  findAuthor().
    then(findBooksByAuthor).
    then(function(books){
      // found books
  }).catch(function(reason){
    // something went wrong
  });
  ```
   @method then
  @param {Function} onFulfilled
  @param {Function} onRejected
  Useful for tooling.
  @return {Promise}
  */

  /**
  `catch` is simply sugar for `then(undefined, onRejection)` which makes it the same
  as the catch block of a try/catch statement.
  ```js
  function findAuthor(){
  throw new Error('couldn't find that author');
  }
  // synchronous
  try {
  findAuthor();
  } catch(reason) {
  // something went wrong
  }
  // async with promises
  findAuthor().catch(function(reason){
  // something went wrong
  });
  ```
  @method catch
  @param {Function} onRejection
  Useful for tooling.
  @return {Promise}
  */


  Promise.prototype.catch = function _catch(onRejection) {
    return this.then(null, onRejection);
  };

  /**
    `finally` will be invoked regardless of the promise's fate just as native
    try/catch/finally behaves
  
    Synchronous example:
  
    ```js
    findAuthor() {
      if (Math.random() > 0.5) {
        throw new Error();
      }
      return new Author();
    }
  
    try {
      return findAuthor(); // succeed or fail
    } catch(error) {
      return findOtherAuther();
    } finally {
      // always runs
      // doesn't affect the return value
    }
    ```
  
    Asynchronous example:
  
    ```js
    findAuthor().catch(function(reason){
      return findOtherAuther();
    }).finally(function(){
      // author was either found, or not
    });
    ```
  
    @method finally
    @param {Function} callback
    @return {Promise}
  */


  Promise.prototype.finally = function _finally(callback) {
    var promise = this;
    var constructor = promise.constructor;

    if (isFunction(callback)) {
      return promise.then(function (value) {
        return constructor.resolve(callback()).then(function () {
          return value;
        });
      }, function (reason) {
        return constructor.resolve(callback()).then(function () {
          throw reason;
        });
      });
    }

    return promise.then(callback, callback);
  };

  return Promise;
}();

Promise$1.prototype.then = then;
Promise$1.all = all;
Promise$1.race = race;
Promise$1.resolve = resolve$1;
Promise$1.reject = reject$1;
Promise$1._setScheduler = setScheduler;
Promise$1._setAsap = setAsap;
Promise$1._asap = asap;

/*global self*/
function polyfill() {
  var local = void 0;

  if (typeof global !== 'undefined') {
    local = global;
  } else if (typeof self !== 'undefined') {
    local = self;
  } else {
    try {
      local = Function('return this')();
    } catch (e) {
      throw new Error('polyfill failed because global object is unavailable in this environment');
    }
  }

  var P = local.Promise;

  if (P) {
    var promiseToString = null;
    try {
      promiseToString = Object.prototype.toString.call(P.resolve());
    } catch (e) {
      // silently ignored
    }

    if (promiseToString === '[object Promise]' && !P.cast) {
      return;
    }
  }

  local.Promise = Promise$1;
}

// Strange compat..
Promise$1.polyfill = polyfill;
Promise$1.Promise = Promise$1;

return Promise$1;

})));



//# sourceMappingURL=es6-promise.map


/***/ }),

/***/ "./node_modules/_mini-css-extract-plugin@0.4.4@mini-css-extract-plugin/dist/loader.js?!./node_modules/_css-loader@0.28.11@css-loader/index.js?!./node_modules/_postcss-loader@3.0.0@postcss-loader/src/index.js?!./node_modules/_px2rem-loader@0.1.9@px2rem-loader/index.js?!./node_modules/_less-loader@4.1.0@less-loader/dist/cjs.js?!./src/index.less":
/*!**************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/_mini-css-extract-plugin@0.4.4@mini-css-extract-plugin/dist/loader.js??ref--10-1!./node_modules/_css-loader@0.28.11@css-loader??ref--10-2!./node_modules/_postcss-loader@3.0.0@postcss-loader/src??ref--10-3!./node_modules/_px2rem-loader@0.1.9@px2rem-loader??ref--10-4!./node_modules/_less-loader@4.1.0@less-loader/dist/cjs.js??ref--10-5!./src/index.less ***!
  \**************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// extracted by mini-css-extract-plugin

/***/ }),

/***/ "./node_modules/_vue-style-loader@4.1.2@vue-style-loader/lib/addStylesClient.js":
/*!**************************************************************************************!*\
  !*** ./node_modules/_vue-style-loader@4.1.2@vue-style-loader/lib/addStylesClient.js ***!
  \**************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "default", function() { return addStylesClient; });
/* harmony import */ var _listToStyles__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./listToStyles */ "./node_modules/_vue-style-loader@4.1.2@vue-style-loader/lib/listToStyles.js");
/*
  MIT License http://www.opensource.org/licenses/mit-license.php
  Author Tobias Koppers @sokra
  Modified by Evan You @yyx990803
*/



var hasDocument = typeof document !== 'undefined'

if (typeof DEBUG !== 'undefined' && DEBUG) {
  if (!hasDocument) {
    throw new Error(
    'vue-style-loader cannot be used in a non-browser environment. ' +
    "Use { target: 'node' } in your Webpack config to indicate a server-rendering environment."
  ) }
}

/*
type StyleObject = {
  id: number;
  parts: Array<StyleObjectPart>
}

type StyleObjectPart = {
  css: string;
  media: string;
  sourceMap: ?string
}
*/

var stylesInDom = {/*
  [id: number]: {
    id: number,
    refs: number,
    parts: Array<(obj?: StyleObjectPart) => void>
  }
*/}

var head = hasDocument && (document.head || document.getElementsByTagName('head')[0])
var singletonElement = null
var singletonCounter = 0
var isProduction = false
var noop = function () {}
var options = null
var ssrIdKey = 'data-vue-ssr-id'

// Force single-tag solution on IE6-9, which has a hard limit on the # of <style>
// tags it will allow on a page
var isOldIE = typeof navigator !== 'undefined' && /msie [6-9]\b/.test(navigator.userAgent.toLowerCase())

function addStylesClient (parentId, list, _isProduction, _options) {
  isProduction = _isProduction

  options = _options || {}

  var styles = Object(_listToStyles__WEBPACK_IMPORTED_MODULE_0__["default"])(parentId, list)
  addStylesToDom(styles)

  return function update (newList) {
    var mayRemove = []
    for (var i = 0; i < styles.length; i++) {
      var item = styles[i]
      var domStyle = stylesInDom[item.id]
      domStyle.refs--
      mayRemove.push(domStyle)
    }
    if (newList) {
      styles = Object(_listToStyles__WEBPACK_IMPORTED_MODULE_0__["default"])(parentId, newList)
      addStylesToDom(styles)
    } else {
      styles = []
    }
    for (var i = 0; i < mayRemove.length; i++) {
      var domStyle = mayRemove[i]
      if (domStyle.refs === 0) {
        for (var j = 0; j < domStyle.parts.length; j++) {
          domStyle.parts[j]()
        }
        delete stylesInDom[domStyle.id]
      }
    }
  }
}

function addStylesToDom (styles /* Array<StyleObject> */) {
  for (var i = 0; i < styles.length; i++) {
    var item = styles[i]
    var domStyle = stylesInDom[item.id]
    if (domStyle) {
      domStyle.refs++
      for (var j = 0; j < domStyle.parts.length; j++) {
        domStyle.parts[j](item.parts[j])
      }
      for (; j < item.parts.length; j++) {
        domStyle.parts.push(addStyle(item.parts[j]))
      }
      if (domStyle.parts.length > item.parts.length) {
        domStyle.parts.length = item.parts.length
      }
    } else {
      var parts = []
      for (var j = 0; j < item.parts.length; j++) {
        parts.push(addStyle(item.parts[j]))
      }
      stylesInDom[item.id] = { id: item.id, refs: 1, parts: parts }
    }
  }
}

function createStyleElement () {
  var styleElement = document.createElement('style')
  styleElement.type = 'text/css'
  head.appendChild(styleElement)
  return styleElement
}

function addStyle (obj /* StyleObjectPart */) {
  var update, remove
  var styleElement = document.querySelector('style[' + ssrIdKey + '~="' + obj.id + '"]')

  if (styleElement) {
    if (isProduction) {
      // has SSR styles and in production mode.
      // simply do nothing.
      return noop
    } else {
      // has SSR styles but in dev mode.
      // for some reason Chrome can't handle source map in server-rendered
      // style tags - source maps in <style> only works if the style tag is
      // created and inserted dynamically. So we remove the server rendered
      // styles and inject new ones.
      styleElement.parentNode.removeChild(styleElement)
    }
  }

  if (isOldIE) {
    // use singleton mode for IE9.
    var styleIndex = singletonCounter++
    styleElement = singletonElement || (singletonElement = createStyleElement())
    update = applyToSingletonTag.bind(null, styleElement, styleIndex, false)
    remove = applyToSingletonTag.bind(null, styleElement, styleIndex, true)
  } else {
    // use multi-style-tag mode in all other cases
    styleElement = createStyleElement()
    update = applyToTag.bind(null, styleElement)
    remove = function () {
      styleElement.parentNode.removeChild(styleElement)
    }
  }

  update(obj)

  return function updateStyle (newObj /* StyleObjectPart */) {
    if (newObj) {
      if (newObj.css === obj.css &&
          newObj.media === obj.media &&
          newObj.sourceMap === obj.sourceMap) {
        return
      }
      update(obj = newObj)
    } else {
      remove()
    }
  }
}

var replaceText = (function () {
  var textStore = []

  return function (index, replacement) {
    textStore[index] = replacement
    return textStore.filter(Boolean).join('\n')
  }
})()

function applyToSingletonTag (styleElement, index, remove, obj) {
  var css = remove ? '' : obj.css

  if (styleElement.styleSheet) {
    styleElement.styleSheet.cssText = replaceText(index, css)
  } else {
    var cssNode = document.createTextNode(css)
    var childNodes = styleElement.childNodes
    if (childNodes[index]) styleElement.removeChild(childNodes[index])
    if (childNodes.length) {
      styleElement.insertBefore(cssNode, childNodes[index])
    } else {
      styleElement.appendChild(cssNode)
    }
  }
}

function applyToTag (styleElement, obj) {
  var css = obj.css
  var media = obj.media
  var sourceMap = obj.sourceMap

  if (media) {
    styleElement.setAttribute('media', media)
  }
  if (options.ssrId) {
    styleElement.setAttribute(ssrIdKey, obj.id)
  }

  if (sourceMap) {
    // https://developer.chrome.com/devtools/docs/javascript-debugging
    // this makes source maps inside style tags work properly in Chrome
    css += '\n/*# sourceURL=' + sourceMap.sources[0] + ' */'
    // http://stackoverflow.com/a/26603875
    css += '\n/*# sourceMappingURL=data:application/json;base64,' + btoa(unescape(encodeURIComponent(JSON.stringify(sourceMap)))) + ' */'
  }

  if (styleElement.styleSheet) {
    styleElement.styleSheet.cssText = css
  } else {
    while (styleElement.firstChild) {
      styleElement.removeChild(styleElement.firstChild)
    }
    styleElement.appendChild(document.createTextNode(css))
  }
}


/***/ }),

/***/ "./node_modules/_vue-style-loader@4.1.2@vue-style-loader/lib/listToStyles.js":
/*!***********************************************************************************!*\
  !*** ./node_modules/_vue-style-loader@4.1.2@vue-style-loader/lib/listToStyles.js ***!
  \***********************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "default", function() { return listToStyles; });
/**
 * Translates the list format produced by css-loader into something
 * easier to manipulate.
 */
function listToStyles (parentId, list) {
  var styles = []
  var newStyles = {}
  for (var i = 0; i < list.length; i++) {
    var item = list[i]
    var id = item[0]
    var css = item[1]
    var media = item[2]
    var sourceMap = item[3]
    var part = {
      id: parentId + ':' + i,
      css: css,
      media: media,
      sourceMap: sourceMap
    }
    if (!newStyles[id]) {
      styles.push(newStyles[id] = { id: id, parts: [part] })
    } else {
      newStyles[id].parts.push(part)
    }
  }
  return styles
}


/***/ }),

/***/ "./src/index.js":
/*!**********************!*\
  !*** ./src/index.js ***!
  \**********************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

__webpack_require__(/*! ./index.less */ "./src/index.less");

$(function(){
    var upFlag = '0';
    var level = '0';
    var pid = '';
    getList(level,pid,upFlag,baseId);

    //隐藏图标
    $("#changeButton").on('click',function () {
        if ($('.container').hasClass('active')) {
            $('.container').removeClass('active');
        } else {
            $('.container').addClass('active');
        }
        if ($('.container').hasClass('active')){
            $('#createDir').show();
            $('#delFile').show();
            $('#uploadFile').show();
            $('#downloadFile').show();
        } else {
            $('#createDir').hide();
            // $('#delFile').hide();
            // $('#uploadFile').hide();
            // $('#downloadFile').hide();
        }
        var level = '0';
        var pid = '';
        var upFlag = '0';
        getList(level,pid,upFlag,baseId);
    })

    //文件下载
    $("#downloadFile").on('click',function () {
        // var filePathS = "";
        // var ftypes = "";
        // var cbarray = [];
        // $(".ckbox input[type='checkbox']:checked").each(function (index,item) {
        //     cbarray.push($(this).val());
        //     // console.log($(item).parent().parent().find(".fullName").children());
        //     filePathS = filePathS + $(item).parent().parent().find(".fullName").children().find("#fpath").val() + ",";
        //     ftypes = ftypes + $(item).parent().parent().find(".fullName").children().find("#ftype").val() + ",";
        // })
        // // console.log(cbarray);
        // if(cbarray.length == 0){
        //     layer.msg('请选择文件后再下载',
        //         {
        //             icon: 2,
        //             time:2000
        //         });
        //     return;
        // }
        // // console.log(ftypes);
        // if(ftypes.indexOf("DIR") != -1){
        //   layer.msg('不支持文件夹下载',
        //       {
        //         icon : 7,
        //         time : 2000
        //       })
        //     return;
        // }
        // // console.log(encodeURI(filePathS))
        // window.location.href = ctxPath+"/materialManagement/downloadFile/?filePathS=" + encodeURI(filePathS);

      //不是用压缩下载
      var filePath = "";
      var ftype = "";
      var item = $(".ckbox input[type='checkbox']:checked");
      var num = $(".ckbox input[type='checkbox']:checked").length;
      if (num == 0){
        layer.msg('请选择文件后再下载',
                {
                    icon: 2,
                    time:2000
                });
            return;
      }else if (num > 1){
        layer.msg('请选择单个文件进行下载',
            {
              icon: 2,
              time:2000
            });
        return;
      }else if (num == 1){
        filePath = item.parent().parent().find(".fullName").children().find("#fpath").val();
        ftype = item.parent().parent().find(".fullName").children().find("#ftype").val();
        console.log(filePath);
        console.log(ftype);
      }
      if(ftype == "DIR"){
        layer.msg('不支持文件夹下载',
            {
              icon : 7,
              time : 2000
            })
          return;
      }
      window.location.href = ctxPath+"/materialManagement/downloadFileSingle/?path=" + encodeURI(filePath);
    })

    //委托获取fileN绑定
    $("ul").delegate("#clClass","click",function(){
          var upFlag = '0';
          var pid = $(this).find("#fid").val();
          var ftype = $(this).find("#ftype").val();
          var level = (parseInt($(this).find("#flevel").val())+1).toString();
          var baseId = $("#id").val();
          // console.log(baseId);
          if(ftype == 'DIR'){
            // console.log("点击目录获取当前层的pid:"+pid);
            // console.log("点击目录获取当前层的level:"+level);
            $("#fileLev").val(level);
            $("#filePi").val(pid);
            // console.log("赋值后level："+$("#fileLev").val());
            // console.log("赋值后pid："+$("#filePi").val());
            getList(level,pid,upFlag,baseId);
        }else{
          layer.msg('请通过下载按钮进行文件下载',
              {
                  icon : 7,
                  time : 2000
              })
        }
    });

    //获取上一层数据
    $("#upButton").on("click",function(){
        var level = (parseInt($("#fileLev").val())-1).toString();
        var pid = $("#filePi").val();
        var upFlag = '1';
        getList(level,pid,upFlag,baseId);
    });

    //新建资料类别ok
    $("ul").delegate("#doCreDir","click",function(){
      var  initDirValue =   $("#initDir").val();
      /*if(initDirValue == ''){
          layer.msg('请填写新建资料类别名称！',
              {
                  icon: 7,
                  time:2500
              });
        return;
      }*/
      var CSRFTokenV = $("#CSRFTok") .val();
      var now  = new Date();
      var createTime = now.format("yyyy-MM-dd hh:mm:ss");//创建时间
      $(this).parent().parent().parent().find($(".nTime")).val(createTime);
      var fileName = $("#initDir").val();//资料类别名称
      var picPath = '/resources/layuiadmin/pages/materialManagement/img/dir.png';
      var fileType = "DIR";//文件类型
      var fileSize = '-';
      var upFlag = '0';
      var fileLevel;
      var filePid;
      var a = $("#fileLev").val();
      var b =$("#filePi").val();
      if(a=='0' && b==''){
        fileLevel = '0';//文件等级
        filePid = '';
      }
      else{
        fileLevel = a;
        filePid=b;
      }
      // console.log("新建资料类别OK时的level:"+fileLevel);
      // console.log("新建资料类别OK时的pid:"+filePid);
      var reg = /\s/g;
      // console.log("文件名是："+fileName);
      // console.log("验证结果是："+reg.test(fileName));
      if(!reg.test(fileName)){
          $.ajax({
            url : "./checkFN",
            type: "post",
            dataType: "json",
            data:{
              fileName : fileName,
              fileLevel:fileLevel,
              baseId:baseId
            },
            success : function (data) {
                if(data.responseCode == 0){
                    $.ajax({
                        url: "./saveDir",
                        type: "post",
                        dataType: "json",
                        data:{
                            fileType:fileType,
                            fileName:fileName,
                            fileLevel:fileLevel,
                            filePid:filePid,
                            fileSize:fileSize,
                            createTime:createTime,
                            picPath:picPath,
                            CSRFToken:CSRFTokenV,
                            baseId:baseId
                        },
                        success: function (data) {
                            // console.log(data);
                            if (data.responseCode == 0) {
                                $(".wait").removeClass("wait");
                                $("#initDir").css({
                                    "border":0,
                                    "background-color":'rgba(255,255,255,.7)',
                                    "cursor":'pointer',
                                })
                                $("#initDir").attr("readonly","readonly");
                                $(".dirButton").hide();
                                layer.msg('新建文件夹成功',
                                    {
                                        icon: 1,
                                        time:2000
                                    });
                                getList(fileLevel,filePid,upFlag,baseId);
                            } else {
                                layer.msg(data.msg,
                                    {
                                        icon: 2,
                                        time:2000
                                    });
                            }
                        }
                    })
                }else {
                    layer.msg(data.msg,
                        {
                            icon: 7,
                            time:3000
                        });
                }
            }
        })
      }else{
          layer.msg('请填写正确的文件夹名称，注意空白符！', {icon: 7,time:3000});
          return;
      }
    })

    //新建资料类别Del
    $("ul").delegate("#delCreDir","click",function(){
        $(".wait").remove();
    })

    //删除资料类别
    $("form").delegate("#delFile","click",function() {
      //获取选中的checkbox值
      var cbarray = [];
      var ids = "";
      var ftypes = "";
      $(".ckbox input[type='checkbox']:checked").each(function (index, item) {
        cbarray.push($(this).val());
        $(item).parent().parent().addClass("delflag");
        ids = ids + $(item).parent().parent().find(".fullName").children().find("#fid").val() + ",";
        ftypes = ftypes + $(item).parent().parent().find(".fullName").children().find("#ftype").val() + ",";
      })
      // console.log("传送到后台的要删除的ids:" + ids);
      // console.log(ids.substring(0,ids.length-1));
      // console.log("ftypes= " + ftypes);
      // console.log(ftypes.substring(0,ftypes.length-1));
      if (cbarray.length == 0) {
        layer.msg('请选择文件后再删除',
            {
              icon: 2,
              time: 2000
            });
        return;
      }
      //如果选中文件夹
      if (ftypes.indexOf("DIR") != -1) {
        //依次判断每个文件夹下是否存在文件或者文件夹，如存在，则不允许删除
        $.ajax({
          url: ctxPath + "/materialManagement/isDirEmpty",
          dataType: "json",
          type: "post",
          data: {
            ids: ids.substring(0,ids.length-1),
            ftypes:ftypes.substring(0,ftypes.length-1),
          },
          success: function (data) {
            if (data.responseCode == 0) {
              //所选文件夹全为空
              layer.confirm('所选择文件夹全为空，确定删除所选的全部文件吗？', function (index) {
                $.ajax({
                  url: ctxPath + "/materialManagement/delete",
                  dataType: "json",
                  type: "post",
                  data: {
                    ids: ids
                  },
                  success: function (data) {
                    if (data.responseCode == 0) {
                      $(".delflag").remove();
                      layer.msg('删除成功',
                          {
                            icon: 1,
                            time: 2000
                          })
                    } else {
                      layer.msg('删除失败',
                          {
                            icon: 2,
                            time: 2000
                          })
                    }
                  }
                })
              })
            } else {
              //所选文件夹不全为空
              layer.msg('存在非空文件夹，无法删除',
                  {
                    icon: 2,
                    time: 2000
                  })
            }
          }
        })
      } else {
          layer.confirm('确定删除所选的全部文件吗？', function (index) {
            $.ajax({
              url: ctxPath + "/materialManagement/delete",
              dataType: "json",
              type: "post",
              data: {
                ids: ids
              },
              success: function (data) {
                if (data.responseCode == 0) {
                  $(".delflag").remove();
                  layer.msg('删除成功',
                      {
                        icon: 1,
                        time: 2000
                      })
                } else {
                  layer.msg('删除失败',
                      {
                        icon: 2,
                        time: 2000
                      })
                }
              }
            })
          })
        }
    })

  // //在数组中查找所有出现的x，并返回一个包含匹配索引的数组
  // function findall(a,x){
  //   var results=[],
  //       len=a.length,
  //       pos=0;
  //   while(pos<len){
  //     pos=a.indexOf(x,pos);
  //     if(pos===-1){//未找到就退出循环完成搜索
  //       break;
  //     }
  //     results.push(pos);//找到就存储索引
  //     pos+=1;//并从下个位置开始搜索
  //   }
  //   return results;
  // }

})
    //上传文件
    layui.use('upload', function() {
        layui.upload.render({
            elem: '#uploadFile' //绑定元素
            , url: './upload' //上传接口
            , accept: 'file' //允许上传所有文件类型
            , data: {
                fileLevel: function () {
                    return $("#fileLev").val();
                },
                filePid: function () {
                    return $("#filePi").val();
                },
                filePath: function () {
                    return $("#filePa").val();
                },
                picPath: function () {
                    return $("#picPath").val();
                },
                CSRFToken: function () {
                    return $("#CSRFTok").val();
                },
                baseId: function(){
                  return baseId;
                }
            }
            , done: function (res) {
                //上传完毕回调
                if (res.responseCode == 0) {
                    var materialC = res.entity;
                    // console.log(materialC);
                    // console.log(materialC.fileLevel);
                    getList(materialC.fileLevel,materialC.filePid,0,baseId);
                    layer.msg('上传文件成功',
                        {
                            icon: 1,
                            time: 2000
                        });
                }else{
                    layer.msg(res.msg,
                        {
                            icon: 2,
                            time: 3000
                        });
                }
            }
            , error: function () {
                layer.msg("上传文件错误",
                    {
                        icon: 2,
                        time: 2000
                    });
            }
        })
    });

    //监听新建文件夹
    $("#createDir").on('click',function(){
        $(".newDirLi").css("display","inline-grid");
        var now  = new Date();
        var createTime = now.format("yyyy-MM-dd hh:mm:ss");
        var dirPic = ctxPath+'/resources/layuiadmin/pages/materialManagement/img/dir.png';
        var html = '<li class="wait"><div class="ckbox">\r\n<input type="checkbox" lay-skin="primary" class="cb">\r\n</div>\r\n' +
            '<div class="fullName">\r\n<img src="'+dirPic+'" class="image">\r\n \r\n	<div class="fileName"><input id="initDir" value="新建文件夹" style="color:#666;" /><button type="button" style="background-color:#1e9fff;margin-top: -4px;" id="doCreDir" class="layui-btn layui-btn-xs dirButton"><i class="layui-icon layuiadmin-button-btn layui-icon-ok"></i></button><button type="button" style="background-color:#1e9fff;margin-top: -4px;margin-left:1px;"id="delCreDir" class="layui-btn layui-btn-xs dirButton"><i class="layui-icon layuiadmin-button-btn layui-icon-close"></i></button></div></div>\r\n' +
            '<div class="size">0kb</div>\r\n<div class="upTime"><input class="nTime" style="margin-left:13%;border:0;color:#666;background-color:rgba(255,255,255,.7)" readonly="readonly" value=" '+createTime+'" /></div></li>';
        var b = $(".wait");
        if(b.length==0){
            $(".titleI").parent().after(html);
        }else{
           layer.msg('您尚有未新建完成的文件夹！', {icon: 7,time:3000});
           return;
        }
    })

    //自定义format
    Date.prototype.format = function(format){
        var o = {
            "M+" : this.getMonth()+1, //month
            "d+" : this.getDate(), //day
            "h+" : this.getHours(), //hour
            "m+" : this.getMinutes(), //minute
            "s+" : this.getSeconds(), //second
            "q+" : Math.floor((this.getMonth()+3)/3), //quarter
            "S" : this.getMilliseconds() //millisecond
        }

        if(/(y+)/i.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        for(var k in o) {
            if(new RegExp("("+ k +")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
            }
        }
        return format;
    }

    function getList(level,pid,upFlag,baseId) {
      var dt = (new Date()).getTime();
      $.ajax({
          url: "./mcList",
          type: "post",
          dataType: "json",
          data:{
              fileLevel:level,
              filePid:pid,
              upFlag:upFlag,
              baseId:baseId,
              dt:dt
          },
          success:function(data){
            // console.info(data);
            /*if(data.content.length == '0'){
                $("#fileLev").val('0');
                $("#filePi").val('');
            }*/
              var list = [];
              list = data;
              // console.log(list);
              addHtml(list);
              var a = list[0];
              if(typeof(a) != 'undefined'){
                if(a.fileType == 'DIR'){
                  $("#filePa").val(a.filePath);
                }
                  $("#fileLev").val(a.fileLevel);
                  $("#filePi").val(a.filePid);
              }
              if($("#fileLev").val() == '0'){
                  $("#upButton").hide();
              }else {
                  $("#upButton").show();
              }
              // console.log("赋值后level："+$("#fileLev").val());
              // console.log("赋值后pid："+$("#filePi").val());
              // console.log("赋值后filePath："+$("#filePa").val());
          }
      })
  }

function addHtml(data) {
    var html = '<ul><li class="titleClass">\n<div class="titleI">\n';
    if($('.container').hasClass('active')) {
        var html = '<ul><li class="titleClass">\n<div class="titleI">\n<div class="fullNameT">文件名</div><div class="sizeT">大小</div><div class="upTimeT">修改日期</div></div></li>';
    }
    for (var i = 0; i < data.length; i++) {
        var item = data[i];
        html += __webpack_require__(/*! ./li.tpl */ "./src/li.tpl")({ item: item });
    }
    html += '</div></li></ul>';
    $('.content').html(html);
}

/***/ }),

/***/ "./src/index.less":
/*!************************!*\
  !*** ./src/index.less ***!
  \************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(/*! !../node_modules/_mini-css-extract-plugin@0.4.4@mini-css-extract-plugin/dist/loader.js??ref--10-1!../node_modules/_css-loader@0.28.11@css-loader??ref--10-2!../node_modules/_postcss-loader@3.0.0@postcss-loader/src??ref--10-3!../node_modules/_px2rem-loader@0.1.9@px2rem-loader??ref--10-4!../node_modules/_less-loader@4.1.0@less-loader/dist/cjs.js??ref--10-5!./index.less */ "./node_modules/_mini-css-extract-plugin@0.4.4@mini-css-extract-plugin/dist/loader.js?!./node_modules/_css-loader@0.28.11@css-loader/index.js?!./node_modules/_postcss-loader@3.0.0@postcss-loader/src/index.js?!./node_modules/_px2rem-loader@0.1.9@px2rem-loader/index.js?!./node_modules/_less-loader@4.1.0@less-loader/dist/cjs.js?!./src/index.less");
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var add = __webpack_require__(/*! ../node_modules/_vue-style-loader@4.1.2@vue-style-loader/lib/addStylesClient.js */ "./node_modules/_vue-style-loader@4.1.2@vue-style-loader/lib/addStylesClient.js").default
var update = add("eaeff82c", content, true, {});

/***/ }),

/***/ "./src/li.tpl":
/*!********************!*\
  !*** ./src/li.tpl ***!
  \********************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = function (obj) {
obj || (obj = {});
var __t, __p = '';
with (obj) {
  if($('.container').hasClass('active')){
      __p += '<li>\r\n<div class="ckbox">\r\n<input type="checkbox" lay-skin="primary" class="cb"">\r\n</div>\r\n<div class="fullName">\r\n<div style="cursor:pointer;" id="clClass">\r\n<img src="'+ctxPath+item.picPath+'" class="image">\r\n \r\n	<div id="fileN" class="fileName" >\r\n</div><input id="fid" type="hidden" value="'+item.id+'" /><input id="flevel" type="hidden" value="'+item.fileLevel+'"/><input id="fpath" type="hidden" value="'+item.filePath+'"/><input id="ftype" type="hidden" value="'+item.fileType+'"/><input id="fpid" type="hidden" value="'+item.filePid+'" />' +
          ((__t = ( item.fileName )) == null ? '' : __t) + '\r\n </div></div>\r\n<div class="size">'+item.fileSize+'</div>\r\n<div class="upTime">'+item.createTime+'</div></li>';
  }else{
      __p += '<li>\r\n<div class="ckbox">\r\n<input type="checkbox" lay-skin="primary" class="cb"">\r\n</div>\r\n<div class="fullName">\r\n<div style="cursor:pointer;" id="clClass">\r\n<img src="'+ctxPath+item.picPath+'" class="image">\r\n \r\n	<div id="fileN" class="fileName" >\r\n</div><input id="fid" type="hidden" value="'+item.id+'" /><input id="flevel" type="hidden" value="'+item.fileLevel+'"/><input id="fpath" type="hidden" value="'+item.filePath+'"/><input id="ftype" type="hidden" value="'+item.fileType+'"/><input id="fpid" type="hidden" value="'+item.filePid+'" />' +
          ((__t = ( item.fileName )) == null ? '' : __t) + '\r\n </div></div></li>';
  }
}
return __p
};

/***/ }),

/***/ 0:
/*!*********************************************************!*\
  !*** multi ./config/production.entry.js ./src/index.js ***!
  \*********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

__webpack_require__(/*! ./config/production.entry.js */"./config/production.entry.js");
module.exports = __webpack_require__(/*! ./src/index.js */"./src/index.js");
})
});
//# sourceMappingURL=index.js.map
