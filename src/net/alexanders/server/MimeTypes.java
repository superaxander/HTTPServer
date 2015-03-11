package net.alexanders.server;

public enum MimeTypes
{
    html(new String[]{".html", ".htm"}, "text/html"), text(new String[]{".txt"}, "text/plain"), css(new String[]{".css"}, "text/css");

    private String[] extensions;
    private String mimetype;
    MimeTypes(String[] extensions, String mimetype){
        this.extensions = extensions;
        this.mimetype = mimetype;
    }

    public static MimeTypes checkMimeType(String filename){
        String extension = filename.substring(filename.lastIndexOf('.'));
        for(MimeTypes type : MimeTypes.values()){
            for(String extensionExpected : type.extensions){
                if(extension.equalsIgnoreCase(extensionExpected)){
                    return type;
                }
            }
        }
        return MimeTypes.text;
    }

    public String getMimeType(){
        return this.mimetype;
    }
}
