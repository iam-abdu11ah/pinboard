package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;

public class Clipboard {
	private List<Clip> elemCopies;
	
	private static Clipboard clipboard;
	
	private Clipboard() {
		elemCopies = new ArrayList<Clip>();
	}
	
	public void copyToClipboard(List<Clip> clips) {
		clear();
		for(Clip c : clips) {
			elemCopies.add(c.copy());
		}
	}
	
	public List<Clip> copyFromClipboard(){
		List<Clip> copieDecopie = new ArrayList<Clip>();
		for(Clip c : elemCopies) {
			copieDecopie.add(c.copy());
		}
		
		return copieDecopie;
	}
	
	public void clear() {
		elemCopies.clear();
	}
	
	public boolean isEmpty() {
		return elemCopies.isEmpty();
	}
	
	public static Clipboard getInstance() {
		if(clipboard == null) {
			clipboard = new Clipboard();
		}
		return clipboard;
	}

}
