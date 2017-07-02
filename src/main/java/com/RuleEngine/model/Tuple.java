package com.RuleEngine.model;

public class Tuple<X, Y> { 
	  public X x; 
	  public Y y; 
	  public Tuple(X x, Y y) { 
	    this.x = x; 
	    this.y = y; 
	  } 
	  
	@Override
	public boolean equals(Object obj){
	    if (this == obj)
	        return true;
	    if (obj == null)
	        return false;
	    if (getClass() != obj.getClass())
	        return false;
	    Tuple<X,Y> tuple = (Tuple<X,Y>)obj;
	    return y.equals(tuple.y) && x.equals(tuple.x);
	}
} 