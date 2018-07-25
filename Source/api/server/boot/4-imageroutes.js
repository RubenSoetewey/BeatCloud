'use strict';
const loggedMiddleWare = require('../../controllers/middlewares/logged');
const uploadersLib = require('../../controllers/lib/uploaders');
const fileSystem = require('fs');
const path = require('path');
const config = require('../../config');

module.exports = function upload(app) {
  const uploaders = uploadersLib(app);

  app.post('/image/upload/*', loggedMiddleWare(app));

  app.post('/image/upload/music', uploaders.image.single('image'), async function(req, res) {
    req.file.uid = req.uid;
    var music = await app.models.Music.findById(req.headers.music);

    if (!music) {
      res.status(400).json({msg: 'Music introuvable'});
      return;
    }
    music.imageUrl = config.appUrl + '/public/content/' + req.file.uid;
    music.save();
    res.json(music);
  });
};
