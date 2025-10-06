# YuguiHoApi
### Hecho por:
#### Sara Sofia Sánchez - 2359506
#### Johann Andrey Gonzalez - 2511006

## Instrucciones de Ejecución:

1. Al iniciar el juego, encontrá una pantalla con un tablero que indica el area del jugador y el area de la maquina
2. El jugador es el que debe generar sus cartar, en el boton elegir cartas
![img_5.png](img_5.png)
![img_6.png](img_6.png)
3. Si se inician generando las de las maquina, solicitara que se genere primero las del jugador
![img_7.png](img_7.png)
4. Cuando se generan las del jugador y las de la maquina, el duelo inicia al selecionar una carta por parte del jugador

   ![img_8.png](img_8.png)
5. La maquina sacara una carta al azar de las que tiene en su mano e inicia la batalla automaticamente. 
6. Las cartas que sean derrotadas se eliminan de la mano del jugador o de la maquina (depende de quien haya sido derrotado).
![img_9.png](img_9.png) ![img_10.png](img_10.png)
7. El juego termina cuando ya un jugador ha ganado dos rondas, si queda en empate ambas cartas no van a seguir en el juego.
![img_11.png](img_11.png)

## Explicación del diseño:

- Decicimos generar una GUI, que tenga tres Jpanes, uno con la información de la maquina, otro con la de la batalla y la ultima la del jugador
- En zona de la maquina encontraremos: tres curadros para las cartas de cada moustro y un boton para generarlas de forma aleatoria:
![img_1.png](img_1.png)
- En la zona de batalla, encontraremos dos recuadros que indican la carta del jugador y de la maquina que estan compitiendo.
   ![img_2.png](img_2.png)
- Por ultimo en la zona del jugador encontrarmos tres recuadros para las cartas y un boton para generarlas
![img_3.png](img_3.png)

- El diseño completo se ve de la siguiente forma:
![img_4.png](img_4.png)
