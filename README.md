Task
----------------------------------------------------------------------------------------------
We need to randomly generate a load of publication metadata, full text and images for indexing.

Each publication generated should have its own directory (named after the publication identifier), 
and contain two xml files: publication.xml & fulltext.xml. It should also contain a separate directory
'images' which contains an image: <publication id>.tif.

/<output directory>
  /publication-1
    publication.xml
    fulltext.xml
    /images
      1.tif
  /publication-2
    publication.xml
    fulltext.xml
    /images
      2.tif
  /publication-3
    publication.xml
    fulltext.xml
    /images
      3.tif    
etc...
Examples of each file type are provided here.

Steps
----------------------------------------------------------------------------------------------
1. Get number of publications to generate and directory to output files to. (Use Scanner class, maybe do some
validation to see if number is greater than 0 etc.)
2. Create methods that return a random string of text, a random char and a random number. (These will be used
when adding data to the xml files, use Math.random() method)
3. Create method to generate data. This will have a for loop that will call methods to generate meta data, generate
full text and an image for each publication. (Use DocumentBuilder class, I'll give examples once you get to this bit)
