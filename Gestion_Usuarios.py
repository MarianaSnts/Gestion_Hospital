USUARIOS_DB = {}
NEXT_USER_ID = 1

def _generar_id_usuario():
    global NEXT_USER_ID
    user_id = NEXT_USER_ID
    NEXT_USER_ID += 1
    return user_id

def crear_usuario(nombre: str, rol: str, clave: str) -> int:
    if not all([nombre, rol, clave]):
        print("ERROR: Faltan datos (nombre, rol o clave) para crear el usuario.")
        return -1

    user_id = _generar_id_usuario()

    USUARIOS_DB[user_id] = {
        'nombre': nombre,
        'rol': rol.capitalize(),
        'clave': clave,
        'activo': True
    }
    print(f"CREADO: ID {user_id}, {nombre} ({rol})")
    return user_id

def obtener_usuario_por_id(user_id: int) -> dict | None:
    datos = USUARIOS_DB.get(user_id)
    
    if datos:
        datos_limpios = datos.copy()
        datos_limpios.pop('clave', None)
        return datos_limpios
    else:
        print(f"ALERTA: Usuario con ID {user_id} no encontrado.")
        return None

def listar_todos_los_usuarios() -> list:
    lista = []
    for user_id, data in USUARIOS_DB.items():
        info = data.copy()
        info['id'] = user_id
        info.pop('clave', None)
        lista.append(info)
    return lista

def actualizar_datos_usuario(user_id: int, **cambios) -> bool:
    if user_id not in USUARIOS_DB:
        print(f"ERROR: No se puede actualizar. ID {user_id} no existe.")
        return False

    usuario = USUARIOS_DB[user_id]
    campos_validos = ['nombre', 'rol', 'clave', 'activo']
    actualizado = False

    for campo, valor in cambios.items():
        if campo in campos_validos:
            usuario[campo] = valor
            actualizado = True
        else:
            print(f"ADVERTENCIA: Campo '{campo}' no es actualizable.")

    if actualizado:
        print(f"ACTUALIZADO: Usuario ID {user_id} modificado.")
        return True
    else:
        print("ALERTA: No se aplicaron cambios válidos.")
        return False

def desactivar_usuario(user_id: int) -> bool:
    return actualizar_datos_usuario(user_id, activo=False)

def eliminar_usuario_permanente(user_id: int) -> bool:
    if user_id in USUARIOS_DB:
        del USUARIOS_DB[user_id]
        print(f"ELIMINADO: El usuario con ID {user_id} ha sido borrado.")
        return True
    else:
        print(f"ERROR: ID {user_id} no encontrado para eliminar.")
        return False

def autenticar_usuario(user_id: int, clave_ingresada: str) -> dict | None:
    usuario = USUARIOS_DB.get(user_id)
    
    if not usuario:
        print("FALLO AUTENTICACIÓN: Usuario no existe.")
        return None
    
    if not usuario['activo']:
        print("FALLO AUTENTICACIÓN: Usuario inactivo.")
        return None

    if usuario['clave'] == clave_ingresada:
        info_login = {
            'id': user_id, 
            'nombre': usuario['nombre'], 
            'rol': usuario['rol']
        }
        print(f"LOGIN OK: {usuario['rol']} {usuario['nombre']}")
        return info_login
    else:
        print("FALLO AUTENTICACIÓN: Clave incorrecta.")
        return None