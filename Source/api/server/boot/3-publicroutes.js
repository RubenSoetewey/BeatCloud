'use strict';
const fileSystem = require('fs');
const path = require('path');
const config = require('../../config');

module.exports = function publicRoutes(app) {
  app.get('/public/musics', async function(req, res) {
    try {
      var musics = await app.models.Music.find({});
      for (let music of musics) {
        var file = await app.models.File.findById(music.soundId);
        music.artistName = (await app.models.User.findById(music.userId)).artistName;
        music.uid = file.uid;
      }

      res.json(musics);
    } catch (ex) {
      res.json(ex);
    }
  });

  app.get('/public/content/:uid', async function(req, res) {
    try {
      var file = await app.models.File.findOne({
        where: {
          uid: req.params.uid
        }
      });
      if (!file) {
        res.status(400).json({msg: "Merci de verifier l'uid"});
        return;
      }

      var filePath = path.join(__dirname, '../../uploads/' + file.type + '/' + file.uid);
      var stat = fileSystem.statSync(filePath);

      var mimeType = file.file.mimetype;

      if (mimeType === 'audio/wave') {
        mimeType = 'audio/x-wav';
      }
      var headers = {
        'Content-Type': mimeType,
        'Content-Length': stat.size
      };

      if (file.type === 'plugin') {
        res.setHeader('Content-Disposition', 'attachment; filename="' + file.file.originalname + '"');
      }

      res.writeHead(200, headers);

      var readStream = fileSystem.createReadStream(filePath);
      // We replaced all the event handlers with a simple call to readStream.pipe()
      readStream.pipe(res);
    } catch (ex) {
      console.log(ex);
      res.status(400).json({msg: 'Une erreur est survenue.'});
    }
  });

  app.get('/public/plugins', async function(req, res) {
    var plugins = await app.models.File.find({where: {type: 'plugin'}});
    for (let plugin of plugins) {
      plugin.downloadUrl = config.appUrl + '/public/content/' + plugin.uid;
    }
    res.json(plugins);
  });

  app.get('/public/all', async function(req, res) {
    try {
      var term = req.query.term;

      var musics = await app.models.Music.find({where: {name: {like: term}}});
      for (let music of musics) {
        var file = await app.models.File.findById(music.soundId);
        music.artistName = (await app.models.User.findById(music.userId)).artistName;
        music.uid = file.uid;
      }

      var files = await app.models.File.find({where: {
        name: {like: term},
        type: 'sound'
      }});
      musics = musics.concat(files);
      res.json(musics);
    } catch (ex) {
      res.json(ex);
    }
  });
};
