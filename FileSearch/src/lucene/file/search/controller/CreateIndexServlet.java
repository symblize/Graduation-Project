//package lucene.file.search.controller;
//
//import lucene.file.search.model.FileModel;
//import lucene.file.search.service.IKAnalyzer6x;
//import lucene.file.search.service.ToSentence;
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.document.FieldType;
//import org.apache.lucene.index.IndexOptions;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.apache.tika.exception.TikaException;
//import org.apache.tika.metadata.Metadata;
//import org.apache.tika.parser.AutoDetectParser;
//import org.apache.tika.parser.ParseContext;
//import org.apache.tika.parser.Parser;
//import org.apache.tika.sax.BodyContentHandler;
//import org.xml.sax.SAXException;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static lucene.file.search.service.ToSentence.ArticleToSentences;
//
///**
// * @author:caoguangshuo
// * @date:2019/5/8
// * @descripstion:
// **/
//public class CreateIndexServlet extends HttpServlet {
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
//        Analyzer analyzer_IK = new IKAnalyzer6x();
//
//
//        Analyzer analyzer = new StandardAnalyzer();
//
//        IndexWriterConfig icw = new IndexWriterConfig(analyzer);
//        icw.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
//        Directory dir = null;
//        IndexWriter indexWriter = null;
//        //Path indexPath = Paths.get("web/indexdir");
//        Path indexPath = Paths.get("/Volumes/MAC/NewFileGraIndexdir");
//        FieldType fileType = new FieldType();
//        fileType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
//        fileType.setStored(true);
//        fileType.setTokenized(true);
//        fileType.setStoreTermVectors(true);
//        fileType.setStoreTermVectorPositions(true);
//        fileType.setStoreTermVectorOffsets(true);
//
//        Date start = new Date(); // start time
//        if(!Files.isReadable(indexPath)){
//            System.out.println(indexPath.toAbsolutePath() + "???????????????????????????????????????");
//            System.exit(1);
//        }
//        dir = FSDirectory.open(indexPath);
//        indexWriter = new IndexWriter(dir, icw);
//        ArrayList<FileModel> fileList = (ArrayList<FileModel>)extractFile();
//        //??????fileList???????????????
//        for(FileModel f : fileList){
//            //  ArrayList<String> sentenceList = new ArrayList<String>();
//            ToSentence s = new ToSentence();
//            System.out.println(s.splitfuhao(f.getContent()));
//            List<String> sl = ArticleToSentences(s.splitfuhao(f.getContent()));
//            if(sl.isEmpty()){
//                System.out.println("?????????????????????");
//            }
//            System.out.println("***********************");
//            for (String row : sl) {
//                // sentenceList.add(row);
//                Document doc = new Document();
//                // doc.add(new Field("title", f.getTitle(), fileType));
//                doc.add(new Field("content", row, fileType));
//                indexWriter.addDocument(doc);
//            }
//        }
//        indexWriter.commit();
//        indexWriter.close();
//        dir.close();
//        Date end = new Date();
//        System.out.println("?????????????????????????????????" + (end.getTime() - start.getTime()) + "??????.");
//        response.sendRedirect("http://www.baidu.com");
//    }
//
//
//    public static List<FileModel> extractFile() throws IOException{
//        ArrayList<FileModel> list = new ArrayList<FileModel>();
//        File fileDir = new File("/Volumes/MAC/AddFile");
//        File[] allFiles = fileDir.listFiles();
//        for(File f : allFiles){
//            FileModel sf = new FileModel(f.getName(), ParserExtraction(f));
//            list.add(sf);
//        }
//        return list;
//    }
//    public static String ParserExtraction(File file) {
//        String fileContent = ""; //??????????????????
//        BodyContentHandler handler = new BodyContentHandler(10*1024*1024);
//        Parser parser = new AutoDetectParser();
//        Metadata metadata = new Metadata();
//        FileInputStream inputStream;
//        try {
//            inputStream = new FileInputStream(file);
//            ParseContext context = new ParseContext();
//            parser.parse(inputStream, handler, metadata, context);
//            fileContent = handler.toString();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (TikaException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return fileContent;
//    }
//
//
//
//
//}
