const express = require('express');
const formidable = require('formidable');
const path = require('path');
const app = express();

app.get('/', (req, res) => {
    res.send(`
    <h2>With <code>"express"</code> npm package</h2>
    <form action="/api/upload" enctype="multipart/form-data" method="post">
      <div>Text field title: <input type="text" name="title" /></div>
      <div>File: <input type="file" name="someExpressFiles" multiple="multiple" /></div>
      <input type="submit" value="Upload" />
    </form>
  `);
});

app.post('/uploadImage', (req, res, next) => {
    console.log("post")
    const uploadFolder = path.join(__dirname, "uploaded");
    const form = formidable({ multiples: true, uploadDir: uploadFolder });
    form.parse(req, (err, fields, files) => {
        if (err) {
            next(err);
            return;
        }
        console.log(fields)
        console.log(files)
        files.forEach(async file => {
            console.log(file)
            // await File.create()
        });
        res.json({ fields, files });
    });
});


app.get('/uploadImage', (req, res, next) => {
    console.log("get")
    const uploadFolder = path.join(__dirname, "uploaded");
    const form = formidable({ multiples: true, uploadDir: uploadFolder });
    form.parse(req, (err, fields, files) => {
        if (err) {
            next(err);
            return;
        }
        console.log(fields)
        console.log(files)
        files.forEach(async file => {
            console.log(file)
            // await File.create()
        });
        res.json({ fields, files });
    });
});

app.listen(3000, () => {
    console.log('Server listening on http://localhost:3000 ...');
});