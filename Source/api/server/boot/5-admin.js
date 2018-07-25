var logged = require('../../controllers/middlewares/logged');
var admin = require('../../controllers/middlewares/admin');

module.exports = function upload(app) {
  app.use('/admin/*', logged(app));
  app.use('/admin/*', admin(app));

  app.get('/admin/users', async function(req, res) {
    var users = await app.models.User.find({});
    res.json(users);
  });
  app.post('/admin/user/ban', async function(req, res) {
    var user = await app.models.User.findById(req.body.userId);
    user.banned = !user.banned;
    user = await user.save();
    res.json(await app.models.User.find());
  });

  app.get('/admin/sounds', async function(req, res) {
    try {
      var sounds = await app.models.File.find({where: {type: 'sound'}});
      var results = sounds;

      var musics = await app.models.Music.find({});
      for (let music of musics) {
        var file = await app.models.File.findById(music.soundId);
        results.push({
          name: music.name,
          uid: file.uid,
          type: 'music',
          userId: music.userId,
          file: file.file,
          id: music.id
        });
      }
      res.json(results);
    } catch (ex) {
      res.json(ex);
    }
  });

  app.post('/admin/sound/delete', async function(req, res) {
    try {
      var sound = await app.models.File.findById(req.body.soundId);
      await sound.destroy();
      res.json(sound);
    } catch (ex) {
      res.json(ex);
    }
  });
};
