'use strict';
const loggedMiddleWare = require('../../controllers/middlewares/logged');
const uploadersLib = require('../../controllers/lib/uploaders');

module.exports = function upload(app) {
  const uploaders = uploadersLib(app);

  app.post('/music/upload', loggedMiddleWare(app));
  app.post('/music/upload', uploaders.music.single('music'), function(req, res) {
    res.json(req.file);
  });

  app.post('/music/edit/:id', loggedMiddleWare(app), async function(req, res) {
    var music = await app.models.Music.findById(req.params.id);
    if (!music) {
      res.status(400).json({msg: 'Musique introuvable.'});
      return;
    }

    if (req.user.id.toString() !== music.userId.toString() && req.user.rank < 7) {
      res.status(400).json({msg: "Vous n'avez aucun droit sur cette ressource"});
      return;
    }

    music.name = req.body.name;
    music.imageUrl = req.body.imageUrl;
    res.json(await music.save());
  });

  app.get('/music/user', loggedMiddleWare(app), async function(req, res) {
    try {
      var musicsUser = await app.models.Music.find({
        where: {userId: req.user.id}
      });
      var musics = [];

      for (let music of musicsUser) {
        var file = await app.models.File.findById(music.soundId);
        if (file) {
          musics.push({
            name: music.name,
            artistName: req.user.artistName,
            imageUrl: music.imageUrl,
            id: music.id,
            uid: file.uid,
            type: 'music',
            userId: req.user.id
          });
        }
      }
      res.json(musics);
    } catch (ex) {
      console.log(ex);
      res.json(ex);
    }
  });

  app.get('/music/:id', async function(req, res) {
    var music = await app.models.Music.findById(req.params.id);
    var file = await app.models.File.findById(music.soundId);
    music.uid = file.uid;
    if (!music) {
      res.status(400).json({msg: 'Musique introuvable.'});
      return;
    }
    res.json({
      sound: music
    });
  });

  app.post('/music/remove/:id', loggedMiddleWare(app), async function(req, res) {
    try {
      var music;
      music = await app.models.Music.findById(req.params.id);
      if (!music) {
        res.status(400).json({msg: 'Element introuvable.'});
      }
      if (music.userId.toString() !== req.user.id.toString() && req.user.rank < 7) {
        res.status(400).json({msg: 'Cette musique ne vous appartient pas'});
        return;
      }
      await app.models.File.removeById(music.soundId.toString());
      await app.models.Music.removeById(music.id.toString());
      res.json({
        msg: 'Music supprimé'
      });
    } catch (ex) {
      console.log(ex);
      res.status(400).json(ex);
    }
  });
};
