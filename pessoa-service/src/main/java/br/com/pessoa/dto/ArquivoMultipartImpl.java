package br.com.pessoa.dto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

public class ArquivoMultipartImpl implements MultipartFile{
	
	private String name;
	
	private Long size;
	
	byte[] bytes;
	
	public ArquivoMultipartImpl(String imagemBase64) {
		String[] atributos = imagemBase64.split(",");
		setName(atributos[0]);
		setBytes(Base64.getDecoder().decode(atributos[2]));
	}

	@Override
	public byte[] getBytes() throws IOException {
		return bytes;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getOriginalFilename() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getSize() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void transferTo(File arg0) throws IOException, IllegalStateException {
		// TODO Auto-generated method stub
		
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setSize(Long size) {
		this.size = size;
	}
	
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}
