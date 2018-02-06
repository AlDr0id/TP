package es.ucm.fdi.tp.mvc;

import java.util.ArrayList;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameError;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;

/**
 * An event-driven game engine.
 * Keeps a list of players and a state, and notifies observers
 * of any changes to the game.
 */

	// MODELO
public class GameTable<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObservable<S, A> {

	private S estado;
	private S estadoIni; // Preguntar como inicializar   = new GameState(2);
	private ArrayList<GameObserver<S, A> > obs;
	private boolean finished;
	
	/**
	 * Constructora del modelo
	 * @param initState estado inicial
	 */
    public GameTable(S initState) {
        
    	this.estadoIni = initState;
    	this.estado = initState;// estaria mejor un estado sin fichas por ejemplo
    	this.obs = new ArrayList<>();
    	this.finished = false;
    }
    
    /**
     * Inicia una partida
     */
    public void start() {
        
    	this.estado = this.estadoIni;
    	this.finished = false;
    	    	
    	
    	for(int i = 0; i < this.obs.size(); i++) {
    		
    		this.obs.get(i).notifyEvent(/* evento */ new GameEvent<S, A>(EventType.Start, null/*Poner algo*/, this.estado, null, "El juego ha comenzado:\n")  );
    		
    	}
    }
    
    /**
     * Para una partida
     */
    public void stop() {
       
    	if(this.finished) {
    		
    		GameError err = new GameError("Has parado una partida ya parada!\n");
    		
    		for(int i = 0; i < this.obs.size(); i++) {
        		
        		this.obs.get(i).notifyEvent(/* evento */ new GameEvent<S, A>(EventType.Error, null/*Poner algo*/, this.estado, err, "Error\n")  );
        		
        	}
    		throw err;
    	}
    	else {
    		this.finished = true;   		
    		
    		for(int i = 0; i < this.obs.size(); i++) {
        		
        		this.obs.get(i).notifyEvent(/* evento */ new GameEvent<S, A>(EventType.Stop, null/*Poner algo*/, this.estado, null, "Partida parada\n"));
        		
        	}
    		
    	}
    	
    }
    
    /**
     * Ejecuta una accion
     * @param action accion a realizar
     */
    public void execute(A action) {
    	
    	if(this.finished) {    		
    		GameError error1 = new GameError("Juego ya terminado");
    		
    		for(int i = 0; i < this.obs.size(); i++) {        		
        		this.obs.get(i).notifyEvent(/* evento */ new GameEvent<S, A>(EventType.Error, null/*Poner algo*/, this.estado, error1, "Juego ya terminado.\n")  );
        		
        	}    		
    		throw error1;
    		
    	}
    	else {    		
    		if(this.estado == null) {    			
    			GameError error2 = new GameError("Juego no iniciado");
    			
    			for(int i = 0; i < this.obs.size(); i++) {            		
            		this.obs.get(i).notifyEvent(/* evento */ new GameEvent<S, A>(EventType.Error, null/*Poner algo*/, this.estado, error2, "Error al hacer eso\n")  );
            		
            	}    			
    		}
    		else {
    			try {
    				
   				if( this.estado.isValid(action)){
	    	    		this.estado = action.applyTo(this.estado);
	    	    		
	    	    		for(int i = 0; i < this.obs.size(); i++) {    	        		
	    	        		this.obs.get(i).notifyEvent(/* evento */ new GameEvent<S, A>(EventType.Change, action, this.estado, null, "After action:\n")  );
	    	        		
	    	        	}    	    		
	    	    		if(this.estado.isFinished()) {    	    			
	    	    			this.finished = true;
	    	    			String fin;
	    	    			
	    	    			if(this.estado.getWinner() != -1)
	    	    				fin = "player " + this.estado.getWinner() + " won.\n";
	    	    			else
	    	    				fin = "ha habido un empate\n";
	    	    			
	    	    			
	    	    			for(int i = 0; i < this.obs.size(); i++) {
    	            		
    	            			this.obs.get(i).notifyEvent(/* evento */ new GameEvent<S, A>(EventType.Stop, null/*Poner algo*/, this.estado, null,
    	            				"Partida terminada: " + fin));
    	            			
    	            		}
    	    			}
    				}
   				else {
   					GameError err = new GameError("Movimiento invalido");
   					
   						this.obs.get(this.estado.getTurn()).notifyEvent(/* evento */ new GameEvent<S, A>(EventType.Error, null/*Poner algo*/, this.estado, err, "Movimiento invalido\n")  );
   				}
    	    	} catch(IllegalArgumentException err) {    	    		
    	    		GameError error = new GameError("Has fallado al hacer eso");
    	    		
    	    		for(int i = 0; i < this.obs.size(); i++) {    	        		
    	        		this.obs.get(i).notifyEvent(/* evento */ new GameEvent<S, A>(EventType.Error, null/*Poner algo*/, this.estado, error, "Movimiento invalido\n")  );
    	        		
    	        	}
    	    	}    	        
    		}    		
    	}
        
    }
    
    /**
     * Devuelve el estado actual
     * @return estado
     */
    public S getState() {
        return this.estado;
    }

    /**
     * Agrega un observador
     * @param o observador
     */
    public void addObserver(GameObserver<S, A> o) {
        this.obs.add(o);
    }
    
    /**
     * Quita un observador
     * @param o observador
     */
    public void removeObserver(GameObserver<S, A> o) {
       	this.obs.remove(o);
    }
}
