# Simple test for document extractor

## 1 - Compile
```
mvn clean package
```

## 2 - Run
Given a path as an input, a printout of the extraction is obtained
```
java -jar target/simpleExtractor-bundled-0.0.1-SNAPSHOT.jar  <input-file>
```

## 3 - Supported types
- .pdf
- .ppt
- .pptx
- .doc (coming next)
- .docx (coming next)