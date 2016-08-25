package gov.ed.fsa.drts.object;

public class Attachment {

	private String id = null;
	private String name = null;
	private String content_type = null;
	private long size;
	private byte[] content = null;
	
	public Attachment()
	{
		
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getContentType()
	{
		return content_type;
	}

	public void setContentType(String content_type)
	{
		this.content_type = content_type;
	}

	public long getSize()
	{
		return size;
	}

	public void setSize(long size)
	{
		this.size = size;
	}

	public byte[] getContent()
	{
		return content;
	}

	public void setContent(byte[] content)
	{
		this.content = content;
	}
}
