package edu.buffalo.cse.irf14.index;

public class WriterFactory {
	
	private static WriterFactory instance = null;
	public static final int TEXTWRITER = 1;
	public static final int SERIALIZATIONWRITER = 2;
	private static String indexDir = null;
	
	private WriterFactory()
	{
		
	}
	
	public static WriterFactory getInstance()
	{
		if(instance == null)
		{
			instance = new WriterFactory();
		}
		
		return instance;
	}
	
	public Writer getWriter(int writerType) throws Exception
	{
		if(indexDir == null)
			throw new Exception("indexDir in WriterFactory cannot be null");
		if(writerType == TEXTWRITER)
		{
			//return TextWriter.getInstance(indexDir);
		}
		else
			if(writerType == SERIALIZATIONWRITER)
				return SerializationWriter.getInstance(indexDir);
		return null;		
	}
	
	public static void setIndexDir(String indexDir)
	{
		WriterFactory.indexDir = indexDir;
	}
	
	

}
