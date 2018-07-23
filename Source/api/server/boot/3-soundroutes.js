'use strict';
const loggedMiddleWare = require('../../controllers/middlewares/logged');
const uploadersLib = require('../../controllers/lib/uploaders');

module.exports = function upload(app) {
  const uploaders = uploadersLib(app);

  app.post('/sound/upload', loggedMiddleWare(app));

  app.post('/sound/upload', uploaders.sound.single('file'), function(req, res) {
    res.json(req.file);
  });
  app.get('/sounds/user', loggedMiddleWare(app), async function(req, res) {
    try {
      var sounds = await app.models.File.find({
        where: {type: 'sound', userId: req.user.id}
      });
      sounds.map(sound=>{
        sound.artistName = req.user.artistName;
        sound.type = 'sound';
      });

      res.json(sounds);
    } catch (ex) {
      console.log(ex);
      res.json(ex);
    }
  });
  app.post('/sound/edit/:id', loggedMiddleWare(app), async function(req, res) {
    var sound = await app.models.File.findOne({where: {id: req.params.id, type: 'sound', userId: req.user.id}});
    if (!sound) {
      res.status(400).json({msg: 'Instrument introuvable.'});
      return;
    }
    sound.name = req.body.name;
    res.json({
      sound: await sound.save()
    });
  });

  app.post('/sound/remove/:id', loggedMiddleWare(app), async function(req, res) {
    try {
      var sound;
      sound = await app.models.File.findById(req.params.id);
      if (!sound) {
        res.status(400).json({msg: 'Element introuvable.'});
      }
      if (sound.userId.toString() !== req.user.id.toString() && req.user.rank < 7) {
        res.status(400).json({msg: 'Cet instrument ne vous appartient pas'});
        return;
      }
      await app.models.File.removeById(sound.id.toString());
      res.json({
        msg: 'Sound supprimé'
      });
    } catch (ex) {
      console.log(ex);
      res.status(400).json(ex);
    }
  });
};
