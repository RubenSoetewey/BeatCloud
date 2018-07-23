'use strict';
var fs = require('fs');
var path = require('path');

module.exports = function(app) {
  // Install a `/` route that returns server status
  var dir = __dirname + '/../../uploads';
  if (!fs.existsSync(dir)) {
    fs.mkdirSync(dir);
  }

  app.use('/', function(req, res, next) {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
    res.setHeader('Access-Control-Allow-Credentials', true);
    next();
  });
};
