package nic.ame.app.master.dto;

import nic.ame.app.admin.model.MedicalBoard;

public class BoardMapper {
    public static MedicalBoardDto toDTO(MedicalBoard entity) {
        if (entity == null) {
            return null;
        }

        MedicalBoardDto dto = new MedicalBoardDto();
        dto.setBoardId(entity.getBoardId());
        dto.setUsedFor(entity.getUsedFor());
        dto.setBoardAtForceNo(entity.getBoardAtForceNo());
        dto.setPlace(entity.getPlace());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setFromDate(entity.getFromDate());
        dto.setToDate(entity.getToDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setBoardYear(entity.getBoardYear());
        dto.setGazettedFlag(entity.getGazettedFlag());
        dto.setIpAddress(entity.getIpAddress());

        return dto;
    }

    public static MedicalBoard toEntity(MedicalBoardDto dto) {
        if (dto == null) {
            return null;
        }

        MedicalBoard entity = new MedicalBoard();
        entity.setBoardId(dto.getBoardId());
        entity.setUsedFor(dto.getUsedFor());
        entity.setBoardAtForceNo(dto.getBoardAtForceNo());
        entity.setPlace(dto.getPlace());
        entity.setCreatedOn(dto.getCreatedOn());
        entity.setFromDate(dto.getFromDate());
        entity.setToDate(dto.getToDate());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setModifiedBy(dto.getModifiedBy());
        entity.setBoardYear(dto.getBoardYear());
        entity.setGazettedFlag(dto.getGazettedFlag());
        entity.setIpAddress(dto.getIpAddress());

        return entity;
    }
}

