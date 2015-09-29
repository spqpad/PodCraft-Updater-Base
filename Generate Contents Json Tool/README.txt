
GenerateContentsJson is a tool that generates a recursive list of all 
the files in the given directory with hash values, and creates 
a JSON file of them.

Usage: java -jar GenerateContentsJson directory

JSON format:
{
 
  "files":[
    {"qualifiedName":".\a", "md5":"56b5c76820a22861686d38a95e51c4ce"}, 
    {"qualifiedName":".\folder1\b.txt", "md5":"d41d8cd98f00b204e9800998ecf8427e"}, 
    {"qualifiedName":".\x", "md5":"3723ba491b0807da613b9e87c2d21f82"},
    ...
  ]
  
}