package cn.cerc.jui.phone;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Image;
import cn.cerc.jpage.vcl.Span;
import cn.cerc.jpage.vcl.TextBox;

/**
 * 
 * @author 善贵
 *
 */
public class Block122 extends Component {
	private TextBox input = new TextBox();
	private Image image = new Image();
	private String content = new String();
	private String placeholder = new String();
	private Map<String, String> items = new LinkedHashMap<String, String>();

	/**
	 * 用于select控件
	 * 
	 * @param owner
	 *            内容显示区
	 */
	public Block122(Component owner) {
		super(owner);
		input.setReadonly(true);
		input.setType("hidden");
		image.setSrc("jui/phone/block107-expand.png");
	}

	@Override
	public void output(HtmlWriter html) {
		String onclick = String.format("javascript:showChoice(\"%s\");", this.getId());
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block122'>", this.getId());
		html.print("<div onclick='%s'>", onclick);
		input.setId(this.getId() + "input");
		input.output(html);
		html.println("<div class='content'");
		if (!"".equals(placeholder))
			html.println("placeholder='%s'", placeholder);
		html.println(">");
		if (!"".equals(content))
			html.println(content);
		html.println("</div>");
		image.output(html);
		html.println("</div>");
		html.println("<div id='%schoice' class='choice2'>", this.getId());
		html.print("<div class='choice3'>");
		html.println("</div>");
		html.print("<div id='%slist' class='choice4'>", this.getId());
		html.print("<ul class=''>");
		for (String key : items.keySet())
			outputChoiceItem(html, key, items.get(key), this.getId());
		html.println("</ul>");
		html.println("</div>");
		html.println("</div>");
		html.println("</div>");
	}

	public TextBox getInput() {
		return input;
	}

	public Span getCaption() {
		return input.getCaption();
	}

	public Map<String, String> getItems() {
		return items;
	}

	public static void outputChoiceItem(HtmlWriter html, String key, String value, String choiceId) {
		html.print("<li onclick='javascript:selectItem(this, \"%s\", \"%s\")'>%s</li>", choiceId, key, value);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
}