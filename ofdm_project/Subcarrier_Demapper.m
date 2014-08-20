function [ Demapped_Data ] = Subcarrier_Demapper( After_FFT )

Demapped_Data=zeros(300,140);

Demapped_Data(1:150,:)=After_FFT(101:250,:);
Demapped_Data(151:300,:)=After_FFT(263:412,:);
%UNTITLED5 Summary of this function goes here
%   Detailed explanation goes here


end

