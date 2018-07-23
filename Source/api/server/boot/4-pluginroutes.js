'use strict';
const loggedMiddleWare = require('../../controllers/middlewares/logged');
const adminMiddleWare = require('../../controllers/middlewares/admin');
const uploadersLib = require('../../controllers/lib/uploaders');
const fileSystem = require('fs');
const path = require('path');
const config = require('../../config');

module.exports = function upload(app) {
  const uploaders = uploadersLib(app);

  app.post('/upload/plugin', loggedMiddleWare(app));
  app.post('/upload/plugin', adminMiddleWare(app));

  app.post('/upload/plugin', uploaders.plugin.single('plugin'), async function(req, res) {
    req.file.uid = req.uid;
    console.log(req.uid);
    var plugins = await app.models.File.find({where: {type: 'plugin'}});
    res.json(plugins);
  });
  app.post('/plugin/remove/:id', loggedMiddleWare(app), adminMiddleWare(app), async function(req, res) {
    await app.models.File.removeById(req.params.id);
    res.json(await app.models.File.find());
  });
};
