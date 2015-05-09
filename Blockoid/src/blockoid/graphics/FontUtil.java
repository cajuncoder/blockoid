package blockoid.graphics;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class FontUtil {
	public static int getHeight(String str, Font font) {
		AffineTransform transform = new AffineTransform();
		FontRenderContext context = new FontRenderContext(transform, true, true); 
		return (int)(font.getStringBounds(str, context).getHeight()) / 2;
	}
	
	public static int getWidth(String str, Font font) {
		AffineTransform transform = new AffineTransform();
		FontRenderContext context = new FontRenderContext(transform, true, true); 
		return (int)(font.getStringBounds(str, context).getWidth());
	}
}
