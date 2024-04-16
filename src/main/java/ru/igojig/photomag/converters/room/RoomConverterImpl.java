package ru.igojig.photomag.converters.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.igojig.photomag.converters.HallConverter;
import ru.igojig.photomag.dtos.rooms.RoomDto;
import ru.igojig.photomag.entities.Room;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoomConverterImpl implements RoomConverter {
    private final HallConverter hallConverter;
//    private final ModelMapper modelMapper;
//
    public RoomDto toDto(Room room){
//        log.info("Start parse Room");
        RoomDto roomDto =new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setName(room.getName());
//        log.info("Print hall: ");
//        log.info("Print hall: " + room.hall);

        roomDto.setHallDto(hallConverter.entityToDto(room.getHall()));
//        log.info("End parse room");
        return roomDto;
//        return null;
    }


//    public RoomDtoDetails entityToRoomDtoDetails(Room room){
//
//        TypeMap<Hall, HallDto> typeMap= modelMapper.createTypeMap(Hall.class, HallDto.class);
//        typeMap.addMapping(new PropertyMap<Hall, HallDto>(){
//            @Override
//            public void configure() {
//                map(source.getId(), destination.getId());
//            }
//
//
//        });

//        RoomDtoDetails roomDtoDetails = modelMapper.map(room, RoomDtoDetails.class);
//        return roomDtoDetails;
//    }




    public Room toEntity(RoomDto roomDto){
        Room room=new Room();
        room.setId(roomDto.getId());
        room.setName(roomDto.getName());
        room.setHall(hallConverter.dtoToEntity(roomDto.getHallDto()));

        return room;
    }


}
