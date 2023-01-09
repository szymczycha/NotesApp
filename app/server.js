const express = require('express');
const bodyParser = require('body-parser');
const formidable = require('formidable');
const path = require('path');
const app = express();
const fs = require('fs');
app.use(
    bodyParser.urlencoded({
        extended: true,
    })
);
app.use(express.static(__dirname+"/uploaded"))
app.get('/', (req, res) => {
    res.send(`
    <div>Chyba dziala</div>
  `);
});

const uploadFolder = path.join(__dirname, "uploaded");
app.post('/uploadImage', async (req, res) => {
    console.log("post")
    const form = formidable({ multiples: true, uploadDir: uploadFolder });
    form.keepExtensions = true;
    form.parse(req, (err, fields, files) => {
        if (err) {
            return;
        }

        console.log(fields)
        console.log(files)
        res.json({ fields, files });
    });
});
app.get('/photos', (req,res) => {
    const data = [];
    fs.readdir(uploadFolder,(err, files)=>{
        if(err){
            return;
        }
        console.log(files);
        files.forEach(file => {
            console.log(path.join(uploadFolder, file))
            const stats = fs.statSync(path.join(uploadFolder, file))
            
            let obj = {
                name: file,
                url: "/uploaded/"+file,
                creationTime: stats.birthtimeMs,
                size: stats.size
            }
            console.log(obj)
            data.push(obj)
            console.log(data);
            
        });
        res.json(data);
    })
})
app.get('/photo', (req,res) => {
    let imageName = req.query.imgName
    console.log(path.join(uploadFolder, imageName))
    res.sendFile(path.join(uploadFolder, imageName));
})

app.listen(3000, () => {
    console.log('Server listening on http://localhost:3000 ...');
});