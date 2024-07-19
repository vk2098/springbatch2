package com.batch.readerwriter.Writer;

import com.batch.readerwriter.Model.StudentCsv;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ItemWriter1 implements ItemWriter<StudentCsv> {

    @Override
    public void write(Chunk chunk) throws Exception {
        chunk.getItems().stream().forEach(e-> System.out.println(e.toString()));
    }
}
