insert into rol (nombre)
values ('ADMIN'), ('DOCENTE'), ('ESTUDIANTE')
on conflict (nombre) do nothing;

insert into permiso (nombre, recurso, accion)
select values_to_insert.nombre, values_to_insert.recurso, values_to_insert.accion
from (values
    ('Gestionar usuarios', 'usuarios', 'escribir'),
    ('Consultar usuarios', 'usuarios', 'leer'),
    ('Gestionar cursos', 'cursos', 'escribir'),
    ('Consultar cursos', 'cursos', 'leer'),
    ('Gestionar notas', 'notas', 'escribir'),
    ('Consultar notas', 'notas', 'leer'),
    ('Gestionar asistencia', 'asistencia', 'escribir'),
    ('Consultar asistencia', 'asistencia', 'leer')
) as values_to_insert(nombre, recurso, accion)
where not exists (
    select 1
    from permiso
    where permiso.recurso = values_to_insert.recurso
      and permiso.accion = values_to_insert.accion
);

insert into rol_permiso (id_rol, id_permiso)
select rol.id_rol, permiso.id_permiso
from rol
join permiso on
    rol.nombre = 'ADMIN'
    or (rol.nombre = 'DOCENTE' and permiso.recurso in ('cursos', 'notas', 'asistencia'))
    or (rol.nombre = 'ESTUDIANTE' and permiso.accion = 'leer')
where not exists (
    select 1
    from rol_permiso
    where rol_permiso.id_rol = rol.id_rol
      and rol_permiso.id_permiso = permiso.id_permiso
);
