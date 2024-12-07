# Requerimientos

Se desea tener un wallet de cuenta que nos permita determinar cuando disponemos de cada moneda.
Al cual se le pueda agregar (cantidades positivas) y quitar (cantidades existentes positivas) de dicha moneda de la cuenta

Se desea monitorear el mercado de valores para saber el precio actual y sus variaciones.
Cada moneda tiene una cotizacion contra otras en el mercado de valores.
Se desea saber el mayor valor de los valores obtenido por la moneda desde que se inicio el monitoreo.
Se desea saber el menor valor de los valores obtenido por la moneda desde que se inicio el monitoreo.
Leer cotizacion actual y ser notificado ante un cambio de cotizacion

Se desea definir un conjunto de reglas en base a los valores de la moneda que de cumplirse realicen compras o ventas.
(Se debe especificar cuales quiere el usuario y se especifican estas:)

1. Si el valor actual cayo un 5% del maximo entonces vender 0.1 unidades de la moneda y reiniciar el monitoreo del maximo
2. Si el valor actual aumento un 5% del minimo entonces comprar 0.1 unidades de la moneda y reiniciar el monitoreo del minimo
3. Si compre 0.1 y el valor actual es mayor o igual al que compre, vendo el 0.1
4. Si vendi 0.1 y paso mas de 5 minutos y el valor actual es menor o igual al que vendi recompro el 0.1