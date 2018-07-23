'use strict';

var crypto = require('crypto');

function _hash(str) {
  const hash = crypto.createHash('sha256');
  hash.update(str);
  return hash.digest('hex');
}

module.exports = function(File) {
  File.prototype.getUid = function(user, str) {
    return this.type + user.id + '-' + Date.now() + '-' + _hash(str);
  };
};
