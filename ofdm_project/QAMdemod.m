function[QAM_demodulated_data]=QAMdemod(demuxed_data)
% Demodulation

QAM_demodulated_data=zeros(1,160000); % Initializing a vector to store the demodulated data

denormalized_data_QAM=demuxed_data; %Denormalizing the data

predefined_symbols_QAM=[0.135+0.15j;0.135+0.36j;0.36+0.135j; 0.36+0.36j; 0.135-0.135j; 0.135-0.36j; 0.36-0.135j; 0.36-0.36j; -0.135+0.135j; -0.135+0.36j; -0.36+0.135j; -0.36+0.36j; -0.135-0.36j; -0.135-0.36j; -0.36-0.135j; -0.36-0.36j];
%Determine the distance of the data symbol from each of the pre defined
%symbols, find the minimum distance among them for performing demodulation.
%For this we are using the min function.

s=1; % Points to the first position of the vector which holds the demodulated data 


for i=1:1:length(denormalized_data_QAM)
    
    
    
    [minimum_distance,index]= min(abs(new_data(i)-predefined_symbols_QAM));
    if index ==1; 
       QAM_demodulated_data(s:s+3)=[0 0 0 0];
     elseif index == 2;
        QAM_demodulated_data(s:s+3)=[0 0 0 1];
     elseif index == 3;
        QAM_demodulated_data(s:s+3)=[0 0 1 0];
     elseif index == 4;
        QAM_demodulated_data(s:s+3)=[0 0 1 1];
     elseif index == 5;
        QAM_demodulated_data(s:s+3)=[0 1 0 0];
     elseif index == 6;
        QAM_demodulated_data(s:s+3)=[0 1 0 1];
     elseif index == 7;
        QAM_demodulated_data(s:s+3)=[0 1 1 0];
     elseif index == 8;
        QAM_demodulated_data(s:s+3)=[0 1 1 1];
     elseif index == 9;
        QAM_demodulated_data(s:s+3)=[1 0 0 0];
     elseif index == 10;
        QAM_demodulated_data(s:s+3)=[1 0 0 1];
     elseif index == 11;
        QAM_demodulated_data(s:s+3)=[1 0 1 0];
     elseif index == 12;
        QAM_demodulated_data(s:s+3)=[1 0 1 1];
     elseif index == 13;
        QAM_demodulated_data(s:s+3)=[1 1 0 0];
     elseif index == 14;
        QAM_demodulated_data(s:s+3)=[1 1 0 1];
     elseif index == 15;
        QAM_demodulated_data(s:s+3)=[1 1 1 0];
     elseif index == 16;
        QAM_demodulated_data(s:s+3)=[1 1 1 1];
       
    end        
    s=s+4;
end 
