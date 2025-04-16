package nic.ame.app.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfAppearance;
import com.itextpdf.text.pdf.PdfSignatureAppearance;

@Service
public class MyObjectService {

    @Autowired
    private MyObjectRepository repository;

    public void saveObject(MyObject obj) throws Exception {
        byte[] serializedData = SerializationUtil.serialize(obj);
        MyObjectEntity entity = new MyObjectEntity(serializedData);
        repository.save(entity);
    }
    public void saveObjectApperance(PdfSignatureAppearance obj) throws Exception {
        byte[] serializedData = SerializationUtil.serialize(obj);
        MyObjectEntity entity = new MyObjectEntity(serializedData);
        repository.save(entity);
    }

    public MyObject getObject(Long id) throws Exception {
        MyObjectEntity entity = repository.findById(id).orElse(null);
        if (entity != null) {
            return (MyObject) SerializationUtil.deserialize(entity.getObjectData());
        }
        return null;
    }
    

    public PdfSignatureAppearance getObjectApperaance(Long id) throws Exception {
        MyObjectEntity entity = repository.findById(id).orElse(null);
        if (entity != null) {
            return (PdfSignatureAppearance) SerializationUtil.deserialize(entity.getObjectData());
        }
        return null;
    }
}

