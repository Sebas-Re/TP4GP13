package com.example.tp4gp13.conexion;

public class DataDB {

    //Información de la BD
    public static String host="sql10.freesqldatabase.com";
    public static String port="3306";
    public static String nameBD="sql10734516";
    public static String user="sql10734516";
    public static String pass="6xeNKNiIIB";

    //Información para la conexion
    public static String urlMySQL = "jdbc:mysql://" + host + ":" + port + "/"+nameBD;
    public static String driver = "com.mysql.jdbc.Driver";

    /*
    CREATE TABLE IF NOT EXISTS `Categoria` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `descripcion` varchar(25) NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
    CREATE TABLE IF NOT EXISTS `Articulo` (
    `id` int(11) NOT NULL,
    `nombre` varchar(25) NOT NULL,
    `stock` int(11) NOT NULL,
    `idCategoria` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (idCategoria) REFERENCES Categoria(id)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
    */

    /* INSERT INTO `Articulo`(`descripcion`) VALUES ('verduras')
    INSERT INTO `Articulo`(`descripcion`) VALUES ('frutas')
    INSERT INTO `Articulo`(`descripcion`) VALUES ('almacen')
    INSERT INTO `Articulo`(`descripcion`) VALUES ('bazar')
     */

}
