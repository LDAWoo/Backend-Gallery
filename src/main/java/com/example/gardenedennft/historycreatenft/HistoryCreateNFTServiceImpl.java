package com.example.gardenedennft.historycreatenft;

import com.example.gardenedennft.constant.SystemConstant;
import com.example.gardenedennft.exception.ResourceDuplicateException;
import com.example.gardenedennft.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoryCreateNFTServiceImpl implements HistoryCreateNFTService{

    private final HistoryCreateNFTResponse historyCreateNFTResponse;

    private final HistoryCreateNFTDTOMapper historyCreateNFTDTOMapper;
    @Override
    public UUID createHistoryNFT(HistoryCreateNFTRequest request) {
        HistoryCreateNFT createHistoryNFT =  historyCreateNFTResponse.save(
                HistoryCreateNFT.builder()
                        .email(request.getEmail())
                        .build()
        );

        return createHistoryNFT.getId();
    }

    @Override
    public void updateHistoryCreateNFT(HistoryCreateNFT historyCreateNFT) {
        historyCreateNFTResponse.findById(historyCreateNFT.getId()).ifPresent(existingHistory -> {
            Field[] fields = HistoryCreateNFT.class.getDeclaredFields();

            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object newValue = field.get(historyCreateNFT);
                    if (newValue != null) {
                        field.set(existingHistory, newValue);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            existingHistory.setLastModifiedDate(new Date());
            historyCreateNFTResponse.save(existingHistory);});
    }

    @Override
    public HistoryCreateNFT findHistoryCreateNFTById(UUID id) {
        HistoryCreateNFT historyCreateNFT = historyCreateNFTResponse.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("History Create NFT not found with" + id));

        if(historyCreateNFT.getStatus()== SystemConstant.STATUS_HISTORY_CREATE_NFT_ACTIVE)
            throw new ResourceDuplicateException("Cannot get history create nft with active");

        return historyCreateNFT;
    }

    @Override
    public HistoryCreateNFT findStatusHistoryCreateNFTById(UUID id, Integer status) {
        return historyCreateNFTResponse.findStatusHistoryCreateNFTById(id,status);
    }

    @Override
    public HistoryCreateNFTRepo findHistoryCreateNFTByEmail(String email) {
        List<HistoryCreateNFT> historyCreateNFTS = historyCreateNFTResponse.findHistoryCreateNFTByEmailAndStatusNot(email,SystemConstant.STATUS_HISTORY_CREATE_NFT_NO_ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Find history create nft not found with email by "+email));

       return HistoryCreateNFTRepo.builder()
               .listResult(
                       historyCreateNFTS
                               .stream()
                               .map(historyCreateNFTDTOMapper)
                               .toList())
                               .build();

    }

    @Override
    public void updateStatusHistoryCreateNFTById(UUID id) {
        historyCreateNFTResponse.updateStatusHistoryCreateNFTById(id);
    }
}
