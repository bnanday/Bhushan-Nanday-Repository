function [ OFDM_Rx_Symbol ] = Remove_CP( Down_Symbol )

OFDM_Rx_Symbol = zeros(512,140);
counter=1;
for k = 37:1:548
    OFDM_Rx_Symbol(counter,:) = Down_Symbol(k,:);
    counter = counter+1;
end




end

