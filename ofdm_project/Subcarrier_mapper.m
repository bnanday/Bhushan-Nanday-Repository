function [ mapped_data ] = Subcarrier_mapper( multiplexed_data )

mapped_data=zeros(512,140);

mapped_data(101:250,:)=multiplexed_data(1:150,:);
mapped_data(263:412,:)=multiplexed_data(151:300,:);


end