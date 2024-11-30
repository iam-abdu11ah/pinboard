package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;

public class Clipboard {
	private List<Clip> elemCopies;
	
	private static Clipboard clipboard;
	private List<ClipboardListener> listeners ;
	
	private Clipboard() {
		elemCopies = new ArrayList<Clip>();
		listeners = new ArrayList<>();
	}
	
	public void copyToClipboard(List<Clip> clips) {
		clear();
		for(Clip c : clips) {
			elemCopies.add(c.copy());
		}
		
		for(ClipboardListener cbL : listeners) {
			cbL.clipboardChanged();
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
		
		//pour pouvoir griser l'option Paste, car il y aura pas d'elelment
		for(ClipboardListener cbL : listeners) {
			cbL.clipboardChanged();
		}
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
	
	public void addListener(ClipboardListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(ClipboardListener listener) {
		listeners.remove(listener);
	}

}
